package com.gratiStore.api_gratiStore.infra.adapter.calculadora.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.calculadora.CalculadoraAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculadoraAdapterImpl implements CalculadoraAdapter {

    private final LojaService lojaService;

    @Override
    public Calculadora calculadoraRequestToCalculadora(CalculadoraRequest request) {
        var calculadora = new Calculadora();
        var loja = lojaService.buscarLoja(request.lojaId());
        calculadora.setNome(request.nome());
        calculadora.setPercentualPrimeiroColocado(request.percentualPrimeiroColocado() / 100);
        calculadora.setPercentualSegundoColocado(request.percentualSegundoColocado() / 100);
        calculadora.setPercentualTerceiroColocado(request.percentualTerceiroColocado() / 100);
        calculadora.setPercentualDemaisColocados(request.percentualDemaisColocados() / 100);
        calculadora.setBonusPrimeiroColocado(request.bonusPrimeiroColocado());
        calculadora.setBonusSegundoColocado(request.bonusSegundoColocado());
        calculadora.setBonusTerceiroColocado(request.bonusTerceiroColocado());
        calculadora.setLoja(loja);

        loja.setCalculadora(calculadora);
        lojaService.salvarNoBanco(loja);

        return calculadora;
    }

    @Override
    public CalculadoraResponse calculadoraToCalculadoraResponse(Calculadora calculadora) {
        return new CalculadoraResponse(calculadora);
    }

    @Override
    public Calculadora calculadoraRequestToCalculadora(Calculadora calculadora, CalculadoraRequest request) {
        var loja = lojaService.buscarLoja(request.lojaId());
        calculadora.setNome(request.nome());
        calculadora.setPercentualPrimeiroColocado(request.percentualPrimeiroColocado() / 100);
        calculadora.setPercentualSegundoColocado(request.percentualSegundoColocado() / 100);
        calculadora.setPercentualTerceiroColocado(request.percentualTerceiroColocado() / 100);
        calculadora.setPercentualDemaisColocados(request.percentualDemaisColocados() / 100);
        calculadora.setBonusPrimeiroColocado(request.bonusPrimeiroColocado());
        calculadora.setBonusSegundoColocado(request.bonusSegundoColocado());
        calculadora.setBonusTerceiroColocado(request.bonusTerceiroColocado());
        calculadora.setLoja(loja);

        loja.setCalculadora(calculadora);

        return calculadora;
    }
}
