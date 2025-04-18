package com.gratiStore.api_gratiStore.controller.dto.request.ponto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;

public record PontoRequest(

        @NotNull(message = "A data não pode ser nula")
        @PastOrPresent(message = "A data não pode estar no futuro")
        LocalDate data,

        @NotNull(message = "A entrada não pode ser nula")
        LocalTime entrada,

        @NotNull(message = "O início do almoço não pode ser nulo")
        LocalTime inicioAlmoco,

        @NotNull(message = "O fim do almoço não pode ser nulo")
        LocalTime fimAlmoco,

        @NotNull(message = "A saída não pode ser nula")
        LocalTime saida,

        @NotNull(message = "O ID do atendente não pode ser nulo")
        @Pattern(regexp = "\\d+", message = "O ID do atendente deve conter apenas números")
        Long atendenteId
) {
}
