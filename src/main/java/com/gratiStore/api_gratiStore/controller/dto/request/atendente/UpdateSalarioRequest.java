package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateSalarioRequest(
        @NotNull(message = "O ID do Atendente não pode ser nulo")
        Long id,

        @NotNull(message = "O salário não pode ser nulo")
        @DecimalMin(value = "0", message = "O salário precisa ser maior do que zero")
        BigDecimal salario
) {
}
