package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AtendenteRequest(
        @NotBlank(message = "O nome não pode estar nulo ou em branco")
        String nome,

        @NotNull(message = "O ID da loja não pode estar nulo")
        Long lojaId,

        @Min(value = 0, message = "O salário não pode ser menor do que zero")
        @NotNull(message = "O salário não pode ser nulo")
        BigDecimal salario
) {
}
