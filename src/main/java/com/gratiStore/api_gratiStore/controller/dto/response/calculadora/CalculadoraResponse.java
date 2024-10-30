package com.gratiStore.api_gratiStore.controller.dto.response.calculadora;

import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;

import java.math.BigDecimal;

public record CalculadoraResponse(Long id,
                                  Double percentualPrimeiroColocado, Double percentualSegundoColocado,
                                  Double percentualTerceiroColocado, Double percentualDemaisColocados,
                                  BigDecimal bonusPrimeiroColocado, BigDecimal bonusSegundoColocado,
                                  BigDecimal bonusTerceiroColocado) {

    public CalculadoraResponse(Calculadora calculadora) {
        this(calculadora.getId(), calculadora.getPercentualPrimeiroColocado(), calculadora.getPercentualSegundoColocado(),
                calculadora.getPercentualTerceiroColocado(), calculadora.getPercentualDemaisColocados(),
                calculadora.getBonusPrimeiroColocado(), calculadora.getBonusSegundoColocado(),
                calculadora.getBonusTerceiroColocado());
    }
}
