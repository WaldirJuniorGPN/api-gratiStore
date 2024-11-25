package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import jakarta.validation.constraints.NotNull;

public record AtrasoRequest(

        @NotNull(message = "O ID do atendente não pode ser nulo")
        Long id,

        @NotNull(message = "O parâmetro atraso não pode ser nulo")
        AtrasoStatus atraso,

        @NotNull(message = "O atributo semana não pode ser nulo")
        SemanaUtils semana
) {
}
