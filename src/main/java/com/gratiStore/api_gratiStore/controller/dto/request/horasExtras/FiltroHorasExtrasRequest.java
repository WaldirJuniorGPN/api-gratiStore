package com.gratiStore.api_gratiStore.controller.dto.request.horasExtras;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FiltroHorasExtrasRequest(

                @Min(value = 1, message = "O mês deve ser entre 1 e 12")
                @Max(value = 12, message = "O mês deve ser entre 1 e 12")
                @NotNull(message = "O campo 'mes' é obrigatório")
                Integer mes,

                @Min(value = 2000, message = "O ano deve ser maior ou igual a 2000")
                @NotNull(message = "O campo 'ano' é obrigatório")
                Integer ano,

                @NotNull(message = "O campo 'lojaId' é obrigatório")
                Long lojaId) {
}
