package com.gratiStore.api_gratiStore.infra.adapter;

import com.gratiStore.api_gratiStore.controller.dto.request.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.Loja;

public interface LojaAdapter {

    Loja lojaRequestToLoja(LojaRequest request);

    LojaResponse lojaToLojaResponse(Loja loja);

    Loja lojaRequestToLoja(Loja loja, LojaRequest request);
}
