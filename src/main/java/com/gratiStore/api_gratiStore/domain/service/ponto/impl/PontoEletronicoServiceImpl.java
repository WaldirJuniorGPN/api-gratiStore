package com.gratiStore.api_gratiStore.domain.service.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.adapter.ponto.PontoEletronicoAdapter;
import com.gratiStore.api_gratiStore.infra.repository.PontoEletronicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PontoEletronicoServiceImpl implements PontoEletronicoService {

    private PontoEletronicoAdapter adapter;
    private PontoEletronicoRepository repository;
    private AtendenteService atendenteService;

    @Override
    public void registrarPronto(PontoRequest request) {
        var atendente = atendenteService.buscarNoBanco(request.atendenteId());
        var ponto = adapter.pontoRequestToPonto(request, atendente);
        repository.save(ponto);
    }

    @Override
    public Page<HistoricoResponse> listarHistorico(Pageable pageable) {
        return repository.findAll(pageable)
                .map(adapter::pontoToHistoricoResponse);
    }

    private PontoEletronico buscarNoBanco(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Ponto Eletrônico com id %d não encontrado", id)));
    }
}
