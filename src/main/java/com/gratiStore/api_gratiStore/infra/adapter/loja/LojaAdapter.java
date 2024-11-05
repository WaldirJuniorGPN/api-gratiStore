package com.gratiStore.api_gratiStore.infra.adapter.loja;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;

import java.util.List;

public interface LojaAdapter {

    Loja lojaRequestToLoja(LojaRequest request);

    LojaResponse lojaToLojaResponse(Loja loja);

    Loja lojaRequestToLoja(Loja loja, LojaRequest request);

    List<AtendenteResponse> mapAtendentesToAtendenteResponse(List<Atendente> atendentes);
}
