package com.gratiStore.api_gratiStore.infra.adapter.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.PontoResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.factory.ponto.PontoEletronicoFactory;
import com.gratiStore.api_gratiStore.infra.adapter.ponto.PontoEletronicoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PontoEletronicoAdapterImpl implements PontoEletronicoAdapter {

    private final PontoEletronicoFactory factory;

    @Override
    public PontoEletronico pontoRequestToPonto(PontoRequest request, Atendente atendente) {
        return factory.criar(request, atendente);
    }

    @Override
    public HistoricoResponse pontoToHistoricoResponse(PontoEletronico ponto) {
        return new HistoricoResponse(ponto.getId(),
                ponto.getData(),
                ponto.getEntrada(),
                ponto.getInicioAlmoco(),
                ponto.getFimAlmoco(),
                ponto.getSaida(),
                ponto.getFeriado(),
                ponto.getAtendente().getId());
    }

    @Override
    public PontoResponse pontoToPontoResponse(PontoEletronico ponto) {
        return new PontoResponse(ponto.getId(),
                ponto.getData(),
                ponto.getEntrada(),
                ponto.getInicioAlmoco(),
                ponto.getFimAlmoco(),
                ponto.getSaida(),
                ponto.getAtendente().getNome(),
                ponto.getFeriado(),
                ponto.getAtestado(),
                ponto.getFolga(),
                ponto.getDescontarEmHoras());
    }
}
