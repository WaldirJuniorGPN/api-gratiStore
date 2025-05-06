package com.gratiStore.api_gratiStore.domain.factory.calculadora.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.factory.calculadora.CalculadoraFactory;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculadoraFactoryImpl implements CalculadoraFactory {

    private final LojaService lojaService;

    @Override
    public Calculadora criar(CalculadoraRequest request) {
        var loja = lojaService.buscarLoja(request.lojaId());
        var calculadora = new Calculadora(request.nome(),
                request.percentualPrimeiroColocado(),
                request.percentualSegundoColocado(),
                request.percentualTerceiroColocado(),
                request.percentualDemaisColocados(),
                request.bonusPrimeiroColocado(),
                request.bonusSegundoColocado(),
                request.bonusTerceiroColocado(),
                loja);

        loja.setCalculadora(calculadora);

        return calculadora;
    }
}
