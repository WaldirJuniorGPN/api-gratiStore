package com.gratiStore.api_gratiStore.controller.dto.response.calculadora;

import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;

import java.math.BigDecimal;

public record CalculadoraResponse(Long id, String nome,
                                  Double percentualPrimeiroColocado, Double percentualSegundoColocado,
                                  Double percentualTerceiroColocado, Double percentualDemaisColocados,
                                  BigDecimal bonusPrimeiroColocado, BigDecimal bonusSegundoColocado,
                                  BigDecimal bonusTerceiroColocado, Long lojaId) {

    public CalculadoraResponse(Calculadora calculadora) {
        this(calculadora.getId(), calculadora.getNome(), calculadora.getPercentualPrimeiroColocado(), calculadora.getPercentualSegundoColocado(),
                calculadora.getPercentualTerceiroColocado(), calculadora.getPercentualDemaisColocados(),
                calculadora.getBonusPrimeiroColocado(), calculadora.getBonusSegundoColocado(),
                calculadora.getBonusTerceiroColocado(), calculadora.getLoja().getId());
    }
}
