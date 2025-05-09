package com.gratiStore.api_gratiStore.domain.service.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestVendas;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtrasoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponseVendas;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtrasoResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.exception.PaginacaoInvalidaException;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import com.gratiStore.api_gratiStore.infra.repository.AtendenteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.NAO;
import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.SIM;

@Service
@RequiredArgsConstructor
public class AtendenteServiceImpl implements AtendenteService {

    private final AtendenteRepository repository;
    private final LojaService lojaService;
    private final AtendenteAdapter adapter;

    @Override
    @Transactional
    public AtendenteResponse criar(AtendenteRequest request) {
        var atendente = adapter.atendenteRequestToAtendente(request);
        repository.save(atendente);

        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    @Transactional
    public void uploadSemana(AtendenteRequestPlanilha request, SemanaUtils semana) {

        var loja = lojaService.buscarLoja(request.lojaId());

        var atendentes = Optional.ofNullable(loja.getAtendentes())
                .orElseThrow(() -> new RuntimeException("A lista de atendentes está nula"));

        var atendenteOptional = atendentes.stream()
                .filter(atendente1 -> atendente1.getNome().equals(request.nome()))
                .findFirst();

        var atendente = atendenteOptional.orElseGet(() -> adapter.atendenteRequestToAtendente(request));

        atualizarSemana(atendente, request, semana);

        repository.save(atendente);
    }

    @Override
    @Transactional
    public AtendenteResponse atualizar(Long id, AtendenteRequest request) {
        var atendente = buscarNoBanco(id);
        adapter.atendenteRequestToAtendente(atendente, request);
        repository.save(atendente);

        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    public AtendenteResponse buscar(Long id) {
        var atendente = buscarNoBanco(id);

        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    public Page<AtendenteResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new PaginacaoInvalidaException("A estrutura de paginação está inválida"))
                .map(adapter::atendenteToAtendenteResponse);
    }

    @Override
    public List<AtendenteResponse> listarTodos() {
        var atendentesAtivos = repository.findAllByAtivoTrue();
        if (atendentesAtivos.isEmpty()) {
            throw new RuntimeException("Nenhum atendente ativo encontrado");
        }

        return atendentesAtivos.stream()
                .map(adapter::atendenteToAtendenteResponse)
                .sorted(Comparator.comparing(AtendenteResponse::nome))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var atendente = buscarNoBanco(id);
        atendente.setNome(String.format("%s - Excluído - %s", atendente.getNome(), LocalDateTime.now()));
        defaultAtributos(atendente);

        repository.save(atendente);
    }

    @Override
    public AtendenteResponse converteAtendenteToAtendenteResponse(Atendente atendente) {
        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    @Transactional
    public AtendenteResponseVendas adicionarVendas(Long id, AtendenteRequestVendas request) {
        var atendente = buscarNoBanco(id);
        atendente.setVendasPrimeiraSemana(request.vendasPrimeiraSemana());
        atendente.setVendasSegundaSemana(request.vendasSegundaSemana());
        atendente.setVendasTerceiraSemana(request.vendasTerceiraSemana());
        atendente.setVendasQuartaSemana(request.vendasQuartaSemana());
        atendente.setVendasQuintaSemana(request.vendasQuintaSemana());
        atendente.setVendasSextaSemana(request.vendasSextaSemana());

        atendente.setAtrasoStatusPrimeiraSemana(request.atrasoPrimeiraSemana());
        atendente.setAtrasoStatusSegundaSemana(request.atrasoSegundaSemana());
        atendente.setAtrasoStatusTerceiraSemana(request.atrasoTerceiraSemana());
        atendente.setAtrasoStatusQuartaSemana(request.atrasoQuartaSemana());
        atendente.setAtrasoStatusQuintaSemana(request.atrasoQuintaSemana());
        atendente.setAtrasoStatusSextaSemana(request.atrasoSextaSemana());

        atendente.setQuantidadeAtendimentosPrimeiraSemana(request.quantidadeAtendimentosPrimeiraSemana());
        atendente.setQuantidadeAtendimentosSegundaSemana(request.quantidadeAtendimentosSegundaSemana());
        atendente.setQuantidadeAtendimentosTerceiraSemana(request.quantidadeAtendimentosTerceiraSemana());
        atendente.setQuantidadeAtendimentosQuartaSemana(request.quantidadeAtendimentosQuartaSemana());
        atendente.setQuantidadeAtendimentosQuintaSemana(request.quantidadeAtendimentosQuintaSemana());
        atendente.setQuantidadeAtendimentosSextaSemana(request.quantidadeAtendimentosSextaSemana());

        repository.save(atendente);

        return adapter.atendenteToAtendenteResponseVendas(atendente);
    }

    @Override
    public AtrasoResponse updateAtraso(AtrasoRequest request) {
        var atendente = buscarNoBanco(request.id());
        setterAtraso(atendente, request);
        repository.save(atendente);

        return new AtrasoResponse(atendente.getId(), true, request.semana());
    }

    @Override
    public Atendente buscarNoBanco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Atendente com ID: %d não foi encontrado ou não está ativo", id)));
    }

    private void setterAtraso(Atendente atendente, AtrasoRequest request) {
        switch (request.semana()) {
            case PRIMEIRA -> atendente.setAtrasoStatusPrimeiraSemana(request.atraso() == SIM ? SIM : NAO);
            case SEGUNDA -> atendente.setAtrasoStatusSegundaSemana(request.atraso() == SIM ? SIM : NAO);
            case TERCEIRA -> atendente.setAtrasoStatusTerceiraSemana(request.atraso() == SIM ? SIM : NAO);
            case QUARTA -> atendente.setAtrasoStatusQuartaSemana(request.atraso() == SIM ? SIM : NAO);
            case QUINTA -> atendente.setAtrasoStatusQuintaSemana(request.atraso() == SIM ? SIM : NAO);
            case SEXTA -> atendente.setAtrasoStatusSextaSemana(request.atraso() == SIM ? SIM : NAO);
        }
    }

    private Atendente buscarPeloNome(String nome) {
        return repository.findByNomeAndAtivoTrue(nome);
    }

    private void atualizarSemana(Atendente atendente, AtendenteRequestPlanilha request, SemanaUtils semana) {
        switch (semana) {
            case PRIMEIRA -> {
                atendente.setVendasPrimeiraSemana(request.vendas());
                atendente.setQuantidadeAtendimentosPrimeiraSemana(request.quantidadeAtendimentos());
            }
            case SEGUNDA -> {
                atendente.setVendasSegundaSemana(request.vendas());
                atendente.setQuantidadeAtendimentosSegundaSemana(request.quantidadeAtendimentos());
            }
            case TERCEIRA -> {
                atendente.setVendasTerceiraSemana(request.vendas());
                atendente.setQuantidadeAtendimentosTerceiraSemana(request.quantidadeAtendimentos());
            }
            case QUARTA -> {
                atendente.setVendasQuartaSemana(request.vendas());
                atendente.setQuantidadeAtendimentosQuartaSemana(request.quantidadeAtendimentos());
            }
            case QUINTA -> {
                atendente.setVendasQuintaSemana(request.vendas());
                atendente.setQuantidadeAtendimentosQuintaSemana(request.quantidadeAtendimentos());
            }
            case SEXTA -> {
                atendente.setVendasSextaSemana(request.vendas());
                atendente.setQuantidadeAtendimentosSextaSemana(request.quantidadeAtendimentos());
            }
            default -> throw new IllegalArgumentException("Semana inválida: " + semana);
        }
    }

    private void defaultAtributos(Atendente atendente) {
        atendente.setLoja(null);
        atendente.setAtivo(false);

        removarAtendenteDaLoja(atendente);
        zerarValores(atendente);
    }

    private void removarAtendenteDaLoja(Atendente atendente) {
        var loja = atendente.getLoja();

        if (loja != null) {
            loja.getAtendentes().remove(atendente);
            lojaService.salvarNoBanco(loja);
        }
    }

    private void zerarValores(Atendente atendente) {
        atendente.setVendasPrimeiraSemana(BigDecimal.ZERO);
        atendente.setVendasSegundaSemana(BigDecimal.ZERO);
        atendente.setVendasTerceiraSemana(BigDecimal.ZERO);
        atendente.setVendasQuartaSemana(BigDecimal.ZERO);
        atendente.setVendasQuintaSemana(BigDecimal.ZERO);
        atendente.setVendasSextaSemana(BigDecimal.ZERO);

        atendente.setQuantidadeAtendimentosPrimeiraSemana(0);
        atendente.setQuantidadeAtendimentosSegundaSemana(0);
        atendente.setQuantidadeAtendimentosTerceiraSemana(0);
        atendente.setQuantidadeAtendimentosQuartaSemana(0);
        atendente.setQuantidadeAtendimentosQuintaSemana(0);
        atendente.setQuantidadeAtendimentosSextaSemana(0);

        atendente.setAtrasoStatusPrimeiraSemana(NAO);
        atendente.setAtrasoStatusSegundaSemana(NAO);
        atendente.setAtrasoStatusTerceiraSemana(NAO);
        atendente.setAtrasoStatusQuartaSemana(NAO);
        atendente.setAtrasoStatusQuintaSemana(NAO);
        atendente.setAtrasoStatusSextaSemana(NAO);

        atendente.setBonus(BigDecimal.ZERO);
        atendente.setGratificacao(BigDecimal.ZERO);
        atendente.setTotalVendas(BigDecimal.ZERO);
    }
}
