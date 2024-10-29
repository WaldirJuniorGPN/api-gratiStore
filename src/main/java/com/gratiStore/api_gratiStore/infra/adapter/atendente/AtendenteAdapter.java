package com.gratiStore.api_gratiStore.infra.adapter.atendente;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

public interface AtendenteAdapter {

    Atendente atendenteRequestToAtendente(AtendenteRequest request);

    AtendenteResponse atendenteToAtendenteResponse(Atendente atendente);

    Atendente atendenteReqquestToAtendente(Atendente atendente, AtendenteRequest request);
}
