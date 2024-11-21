package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AtendenteRequestPlanilha(
        @NotBlank
        String nome,

        @NotNull
        Integer quantidadeAtendimentos,

        @NotNull
        BigDecimal vendas,

        @NotNull
        Long lojaId
) {
}
