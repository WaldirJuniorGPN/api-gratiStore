package com.gratiStore.api_gratiStore.infra.adapter.atendente;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponseVendas;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

public interface AtendenteAdapter {

    Atendente atendenteRequestToAtendente(AtendenteRequest request);

    Atendente atendenteRequestToAtendente(AtendenteRequestPlanilha request);

    AtendenteResponse atendenteToAtendenteResponse(Atendente atendente);

    Atendente atendenteRequestToAtendente(Atendente atendente, AtendenteRequest request);

    AtendenteResponseVendas atendenteToAtendenteResponseVendas(Atendente atendente);
}
