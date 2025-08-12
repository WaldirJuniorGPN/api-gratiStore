package com.gratiStore.api_gratiStore.controller.dto.response.ponto;

import com.gratiStore.api_gratiStore.domain.utils.StatusUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public record HistoricoResponse(Long id,
                                LocalDate data,
                                LocalTime entrada,
                                LocalTime inicioAlmoco,
                                LocalTime fimAlmoco,
                                LocalTime saida,
                                StatusUtils status,
                                Long atendenteId) {
}
