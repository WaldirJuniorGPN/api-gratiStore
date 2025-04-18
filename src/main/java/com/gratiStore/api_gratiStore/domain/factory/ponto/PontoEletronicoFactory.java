package com.gratiStore.api_gratiStore.domain.factory;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;

public interface PontoEletronicoFactory {

    PontoEletronico criar(PontoRequest request, Atendente atendente);
}
