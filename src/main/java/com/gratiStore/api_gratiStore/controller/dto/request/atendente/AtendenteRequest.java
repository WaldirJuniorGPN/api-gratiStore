package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import jakarta.validation.constraints.NotBlank;

public record AtendenteRequest(
        @NotBlank(message = "O nome não pode estar nulo ou em branco")
        String nome
) {
}
