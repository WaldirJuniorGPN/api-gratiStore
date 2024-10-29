package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtendenteRequest(
        @NotBlank(message = "O nome não pode estar nulo ou em branco")
        String nome,

        @NotNull(message = "O ID da loja não pode estar nulo")
        Long lojaId
) {
}
