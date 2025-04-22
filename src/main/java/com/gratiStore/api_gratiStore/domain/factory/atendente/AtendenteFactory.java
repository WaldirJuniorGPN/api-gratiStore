package com.gratiStore.api_gratiStore.domain.factory.atendente;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

public interface AtendenteFactory {

    Atendente criar(AtendenteRequest request);

    Atendente criar(AtendenteRequestPlanilha requestPlanilha);
}
