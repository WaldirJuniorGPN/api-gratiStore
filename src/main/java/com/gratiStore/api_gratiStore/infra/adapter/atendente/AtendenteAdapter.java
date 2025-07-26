package com.gratiStore.api_gratiStore.infra.adapter.atendente;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtrasoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.*;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

public interface AtendenteAdapter {

    Atendente atendenteRequestToAtendente(AtendenteRequest request);

    Atendente atendenteRequestToAtendente(AtendenteRequestPlanilha request);

    AtendenteResponse atendenteToAtendenteResponse(Atendente atendente);

    Atendente atendenteRequestToAtendente(Atendente atendente, AtendenteRequest request);

    AtendenteResponseVendas atendenteToAtendenteResponseVendas(Atendente atendente);

    AtrasoResponse atendenteToAtrasoResponse(Atendente atendente, AtrasoRequest request);

    UpdateSalarioResponse atendentetoUpdateSalarioResponse(Atendente atendente);

    SalarioAtendenteResponse atendenteToSalarioAtendenteResponse(Atendente atendente);
}
