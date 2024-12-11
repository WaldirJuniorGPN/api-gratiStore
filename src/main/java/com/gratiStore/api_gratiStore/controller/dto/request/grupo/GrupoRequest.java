package com.gratiStore.api_gratiStore.controller.dto.request.grupo;

import jakarta.validation.constraints.NotBlank;

public record GrupoRequest(
        @NotBlank(message = "O nome do Grupo não pode estar em branco ou nulo")
        String nome
) {
}
