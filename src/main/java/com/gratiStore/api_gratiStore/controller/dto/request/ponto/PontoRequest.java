package com.gratiStore.api_gratiStore.controller.dto.request.ponto;

import com.gratiStore.api_gratiStore.domain.utils.StatusUtils;
import com.gratiStore.api_gratiStore.domain.validator.pontoEletronico.HorariosCoerentes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;

@HorariosCoerentes
public record PontoRequest(

        @NotNull(message = "A data não pode ser nula")
        @PastOrPresent(message = "A data não pode estar no futuro")
        @Schema(type = "string", example = "2025-04-19", format = "date")
        LocalDate data,

        @Schema(type = "string", example = "08:00:00", format = "time")
        LocalTime entrada,

        @Schema(type = "string", example = "12:00:00", format = "time")
        LocalTime inicioAlmoco,

        @Schema(type = "string", example = "13:00:00", format = "time")
        LocalTime fimAlmoco,

        @Schema(type = "string", example = "17:00:00", format = "time")
        LocalTime saida,

        @NotNull(message = "O status não pode ser nulo")
        @Schema(type = "string", example = "FERIADO")
        StatusUtils status,

        @NotNull(message = "O ID do atendente não pode ser nulo")
        @Schema(example = "123")
        Long atendenteId
) {
}
