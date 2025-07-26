package com.gratiStore.api_gratiStore.infra.adapter.calculadora.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.factory.calculadora.CalculadoraFactory;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.calculadora.CalculadoraAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculadoraAdapterImpl implements CalculadoraAdapter {

    private final CalculadoraFactory calculadoraFactory;
    private final LojaService lojaService;

    @Override
    public Calculadora calculadoraRequestToCalculadora(CalculadoraRequest request) {

        return calculadoraFactory.criar(request);
    }

    @Override
    public CalculadoraResponse calculadoraToCalculadoraResponse(Calculadora calculadora) {
        return new CalculadoraResponse(calculadora);
    }

    @Override
    public Calculadora calculadoraRequestToCalculadora(Calculadora calculadora, CalculadoraRequest request) {
        var loja = lojaService.buscarLoja(request.lojaId());
        calculadora.setNome(request.nome());
        calculadora.setPercentualPrimeiroColocado(request.percentualPrimeiroColocado());
        calculadora.setPercentualSegundoColocado(request.percentualSegundoColocado());
        calculadora.setPercentualTerceiroColocado(request.percentualTerceiroColocado());
        calculadora.setPercentualDemaisColocados(request.percentualDemaisColocados());
        calculadora.setBonusPrimeiroColocado(request.bonusPrimeiroColocado());
        calculadora.setBonusSegundoColocado(request.bonusSegundoColocado());
        calculadora.setBonusTerceiroColocado(request.bonusTerceiroColocado());
        calculadora.setLoja(loja);

        loja.setCalculadora(calculadora);

        return calculadora;
    }
}
