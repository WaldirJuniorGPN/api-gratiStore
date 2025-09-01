package com.gratiStore.api_gratiStore.domain.service.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.PontoResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.adapter.ponto.PontoEletronicoAdapter;
import com.gratiStore.api_gratiStore.infra.repository.PontoEletronicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.Validator.validarIdPonto;

@Service
@RequiredArgsConstructor
public class PontoEletronicoServiceImpl implements PontoEletronicoService {

    private final String MSG_ERROR = "Ponto Eletrônico com id %d não encontrado";

    private final PontoEletronicoAdapter adapter;
    private final PontoEletronicoRepository repository;
    private final AtendenteService atendenteService;

    @Override
    @Transactional
    public PontoResponse registrarPonto(PontoRequest request) {
        var atendente = atendenteService.buscarNoBanco(request.atendenteId());
        var ponto = adapter.pontoRequestToPonto(request, atendente);

        var pontoExistente = repository.findByDataAndAtendente(ponto.getData(), atendente);

        if (pontoExistente.isEmpty()) {
            repository.save(ponto);
        }

        return adapter.pontoToPontoResponse(ponto);
    }

    @Override
    public Page<HistoricoResponse> listarHistorico(Pageable pageable) {
        var sort = pageable.getSort().isSorted()
                ? pageable.getSort()
                : Sort.by(Sort.Direction.ASC, "data");
        var sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return repository.findAll(sortedPageable)
                .map(adapter::pontoToHistoricoResponse);
    }

    @Override
    public List<PontoEletronico> listarHistorico(List<Atendente> atendentes, int mes, int ano) {
        var dataInicio = LocalDate.of(ano, mes, 1);
        var dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        return atendentes.stream()
                .flatMap(atendente -> repository
                        .findByAtendenteIdAndDataBetween(atendente.getId(), dataInicio, dataFim)
                        .stream())
                .toList();
    }

    @Override
    @Transactional
    public HistoricoResponse atualizar(Long id, PontoRequest request) {
        var ponto = buscarNoBanco(id);
        var atendente = atendenteService.buscarNoBanco(request.atendenteId());
        ponto.atualizarParametros(request.data(),
                request.entrada(),
                request.inicioAlmoco(),
                request.fimAlmoco(),
                request.saida(),
                request.status(),
                atendente);
        repository.save(ponto);

        return adapter.pontoToHistoricoResponse(ponto);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        validarIdPonto(id);
        repository.deleteById(id);
    }

    @Override
    public HistoricoResponse buscar(Long id) {
        var ponto = buscarNoBanco(id);

        return adapter.pontoToHistoricoResponse(ponto);
    }

    private PontoEletronico buscarNoBanco(Long id) {
        validarIdPonto(id);
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(MSG_ERROR, id)));
    }
}