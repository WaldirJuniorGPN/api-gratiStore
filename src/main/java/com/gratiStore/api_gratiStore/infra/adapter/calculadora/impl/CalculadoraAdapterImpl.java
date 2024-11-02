package com.gratiStore.api_gratiStore.infra.adapter.calculadora.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.infra.adapter.calculadora.CalculadoraAdapter;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraAdapterImpl implements CalculadoraAdapter {


    @Override
    public Calculadora calculadoraRequestToCalculadora(CalculadoraRequest request) {
        var calculadora = new Calculadora();
        calculadora.setNome(request.nome());
        calculadora.setPercentualPrimeiroColocado(request.percentualPrimeiroColocado());
        calculadora.setPercentualSegundoColocado(request.percentualSegundoColocado());
        calculadora.setPercentualTerceiroColocado(request.percentualTerceiroColocado());
        calculadora.setPercentualDemaisColocados(request.percentualDemaisColocados());
        calculadora.setBonusPrimeiroColocado(request.bonusPrimeiroColocado());
        calculadora.setBonusSegundoColocado(request.bonusSegundoColocado());
        calculadora.setBonusTerceiroColocado(request.bonusTerceiroColocado());

        return calculadora;
    }

    @Override
    public CalculadoraResponse calculadoraToCalculadoraResponse(Calculadora calculadora) {
        return new CalculadoraResponse(calculadora);
    }

    @Override
    public Calculadora calculadoraRequestToCalculadora(Calculadora calculadora, CalculadoraRequest request) {
        calculadora.setNome(request.nome());
        calculadora.setPercentualPrimeiroColocado(request.percentualPrimeiroColocado());
        calculadora.setPercentualSegundoColocado(request.percentualSegundoColocado());
        calculadora.setPercentualTerceiroColocado(request.percentualTerceiroColocado());
        calculadora.setPercentualDemaisColocados(request.percentualDemaisColocados());
        calculadora.setBonusPrimeiroColocado(request.bonusPrimeiroColocado());
        calculadora.setBonusSegundoColocado(request.bonusSegundoColocado());
        calculadora.setBonusTerceiroColocado(request.bonusTerceiroColocado());

        return calculadora;
    }
}
