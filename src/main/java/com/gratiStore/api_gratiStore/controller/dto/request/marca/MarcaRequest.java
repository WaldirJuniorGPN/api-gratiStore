package com.gratiStore.api_gratiStore.controller.dto.request.marca;

import jakarta.validation.constraints.NotBlank;

public record MarcaRequest(
        @NotBlank(message = "A marca não pode estar em branco ou nula")
        String nome
) {
}
