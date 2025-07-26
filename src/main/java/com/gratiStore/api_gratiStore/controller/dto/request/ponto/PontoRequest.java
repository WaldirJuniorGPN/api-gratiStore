package com.gratiStore.api_gratiStore.controller.dto.request.ponto;

import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;

public record PontoRequest(

        @NotNull(message = "A data não pode ser nula")
        @PastOrPresent(message = "A data não pode estar no futuro")
        @Schema(type = "string", example = "2025-04-19", format = "date")
        LocalDate data,

        @NotNull(message = "A entrada não pode ser nula")
        @Schema(type = "string", example = "08:00:00", format = "time")
        LocalTime entrada,

        @NotNull(message = "O início do almoço não pode ser nulo")
        @Schema(type = "string", example = "12:00:00", format = "time")
        LocalTime inicioAlmoco,

        @NotNull(message = "O fim do almoço não pode ser nulo")
        @Schema(type = "string", example = "13:00:00", format = "time")
        LocalTime fimAlmoco,

        @NotNull(message = "A saída não pode ser nula")
        @Schema(type = "string", example = "17:00:00", format = "time")
        LocalTime saida,

        @NotNull
        FeriadoUtils feriado,

        @NotNull(message = "O ID do atendente não pode ser nulo")
        @Schema(example = "123")
        Long atendenteId
) {
}
