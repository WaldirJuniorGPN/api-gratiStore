package com.gratiStore.api_gratiStore.controller.dto.response.ponto;

import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public record HistoricoResponse(Long id,
                                LocalDate data,
                                LocalTime entrada,
                                LocalTime inicioAlmoco,
                                LocalTime fimAlmoco,
                                LocalTime saida,
                                FeriadoUtils ferido,
                                Long atendenteId) {
}
