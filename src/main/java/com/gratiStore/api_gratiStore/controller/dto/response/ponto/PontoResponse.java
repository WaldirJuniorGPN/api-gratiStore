package com.gratiStore.api_gratiStore.controller.dto.response.ponto;

import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public record PontoResponse(Long id,
                            LocalDate data,
                            LocalTime entrada,
                            LocalTime incioAlmoco,
                            LocalTime fimAlmoco,
                            LocalTime saida,
                            String nomeAtendente,
                            FeriadoUtils feriado
) {
}
