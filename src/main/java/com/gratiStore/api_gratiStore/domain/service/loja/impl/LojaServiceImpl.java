package com.gratiStore.api_gratiStore.domain.service.loja.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.VendasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.loja.LojaAdapter;
import com.gratiStore.api_gratiStore.infra.repository.LojaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.NAO;

@Service
@RequiredArgsConstructor
public class LojaServiceImpl implements LojaService {

    private final LojaAdapter adapter;
    private final LojaRepository repository;

    @Override
    @Transactional
    public LojaResponse criar(LojaRequest request) {
        var loja = adapter.lojaRequestToLoja(request);
        repository.save(loja);

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    @Transactional
    public LojaResponse atualizar(Long id, LojaRequest request) {
        var loja = buscarNoBanco(id);
        adapter.lojaRequestToLoja(loja, request);
        repository.save(loja);

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    public LojaResponse buscar(Long id) {
        var loja = buscarNoBanco(id);

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    public LojaResponse buscar(String cnpj) {
        var loja = repository.findByCnpjAndAtivoTrue(cnpj).orElseThrow(
                () -> new EntityNotFoundException(String.format("Loja com CNPJ: %d não foi encontrada ou não está ativa", cnpj)));

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    public Page<LojaResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new IllegalArgumentException("A estrutura de paginação está inválida")).map(adapter::lojaToLojaResponse);
    }

    @Override
    public List<LojaResponse> lsitarTodos() {
        var lojasAtivas = repository.findAllByAtivoTrue();
        if (lojasAtivas.isEmpty()) {
            throw new RuntimeException("Nenhuma loja ativa encontrada");
        }

        return lojasAtivas.stream()
                .map(adapter::lojaToLojaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var loja = buscarNoBanco(id);
        loja.setAtivo(false);
        repository.save(loja);
    }

    @Override
    public Loja buscarLoja(Long id) {
        return buscarNoBanco(id);
    }

    @Override
    public List<AtendenteResponse> listarAtendentesPorLoja(Long id) {
        var loja = buscarNoBanco(id);

        return adapter.mapAtendentesToAtendenteResponse(loja.getAtendentes());
    }
    
    @Override
    public void salvarNoBanco(Loja loja) {
        repository.save(loja);
    }
    
    @Override
    @Transactional
    public void zerarValoresAtendentes(Long lojaId) {
        var loja = buscarLoja(lojaId);
        var atendentes = loja.getAtendentes();
        atendentes.forEach(this::zerarValores);
        salvarNoBanco(loja);
    }
    
    @Override
    public VendasResponse buscarVendasTotais(Long id) {
        var loja = buscarNoBanco(id);
        
        return adapter.lojaToVendaResponse(loja);
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

    private Loja buscarNoBanco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Loja com ID: %d não foi encontrada ou não está ativa", id)));
    }

}
