package com.gratiStore.api_gratiStore.domain.factory.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.factory.atendente.AtendenteFactory;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AtendenteFactoryImpl implements AtendenteFactory {

    private final LojaService lojaService;

    @Override
    public Atendente criar(AtendenteRequest request) {
        var loja = lojaService.buscarLoja(request.lojaId());

        return new Atendente(request.nome(), loja, request.salario());
    }

    @Override
    public Atendente criar(AtendenteRequestPlanilha requestPlanilha) {
        var loja = lojaService.buscarLoja(requestPlanilha.lojaId());

        return new Atendente(requestPlanilha.nome(), loja, BigDecimal.ZERO);
    }
}
