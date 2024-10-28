package com.gratiStore.api_gratiStore.controller.dto.request.loja;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LojaRequest(
        @NotBlank(message = "O nome não pode estar nulo ou em branco")
        String nome,

        @NotBlank(message = "O CNPJ não pode estar nulo ou em branco")
        @Pattern(regexp = "^(\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2})$", message = "O CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX ou XXXXXXXXXXXXXX.")
        String cnpj
) {
}
