package com.gratiStore.api_gratiStore.infra.adapter.loja;

import java.util.List;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.VendasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;

public interface LojaAdapter {

    Loja lojaRequestToLoja(LojaRequest request);

    LojaResponse lojaToLojaResponse(Loja loja);

    List<AtendenteResponse> mapAtendentesToAtendenteResponse(List<Atendente> atendentes);

    VendasResponse lojaToVendaResponse(Loja loja);
}
