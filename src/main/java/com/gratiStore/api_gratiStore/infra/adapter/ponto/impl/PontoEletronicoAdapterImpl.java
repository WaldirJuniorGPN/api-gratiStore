package com.gratiStore.api_gratiStore.infra.adapter.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.infra.adapter.ponto.PontoEletronicoAdapter;

public class PontoEletronicoAdapterImpl implements PontoEletronicoAdapter {

    @Override
    public void pontoRequestToPonto(PontoRequest request, Atendente atendente) {

    }

    @Override
    public HistoricoResponse pontoToHistoricoResponse(PontoEletronico ponto) {
        return null;
    }
}
