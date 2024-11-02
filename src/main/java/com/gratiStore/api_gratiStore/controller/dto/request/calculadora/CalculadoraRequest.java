package com.gratiStore.api_gratiStore.controller.dto.request.calculadora;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CalculadoraRequest(

        @NotBlank(message = "O nome não pode estar nulo ou em branco")
        String nome,

        @NotNull(message = "O percentual do primeiro colocado é obrigatório.")
        @Min(value = 0, message = "O percentual do primeiro colocado deve ser maior ou igual a 0.")
        @Max(value = 100, message = "O percentual do primeiro colocado não pode exceder 100.")
        Double percentualPrimeiroColocado,

        @NotNull(message = "O percentual do segundo colocado é obrigatório.")
        @Min(value = 0, message = "O percentual do segundo colocado deve ser maior ou igual a 0.")
        @Max(value = 100, message = "O percentual do segundo colocado não pode exceder 100.")
        Double percentualSegundoColocado,

        @NotNull(message = "O percentual do terceiro colocado é obrigatório.")
        @Min(value = 0, message = "O percentual do terceiro colocado deve ser maior ou igual a 0.")
        @Max(value = 100, message = "O percentual do terceiro colocado não pode exceder 100.")
        Double percentualTerceiroColocado,

        @NotNull(message = "O percentual dos demais colocados é obrigatório.")
        @Min(value = 0, message = "O percentual dos demais colocados deve ser maior ou igual a 0.")
        @Max(value = 100, message = "O percentual dos demais colocados não pode exceder 100.")
        Double percentualDemaisColocados,

        @NotNull(message = "O bônus do primeiro colocado é obrigatório.")
        @DecimalMin(value = "0.00", message = "O bônus do primeiro colocado deve ser maior ou igual a 0.00.")
        BigDecimal bonusPrimeiroColocado,

        @NotNull(message = "O bônus do segundo colocado é obrigatório.")
        @DecimalMin(value = "0.00", message = "O bônus do segundo colocado deve ser maior ou igual a 0.00.")
        BigDecimal bonusSegundoColocado,

        @NotNull(message = "O bônus do terceiro colocado é obrigatório.")
        @DecimalMin(value = "0.00", message = "O bônus do terceiro colocado deve ser maior ou igual a 0.00.")
        BigDecimal bonusTerceiroColocado
) {
}
