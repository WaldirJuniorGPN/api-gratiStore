package com.gratiStore.api_gratiStore.infra.adapter.ponto;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;

public interface PontoEletronicoAdapter {

    PontoEletronico pontoRequestToPonto(PontoRequest request, Atendente atendente);

    HistoricoResponse pontoToHistoricoResponse(PontoEletronico ponto);
}
