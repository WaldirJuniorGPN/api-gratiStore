package com.gratiStore.api_gratiStore.controller.dto.response.atendente;

import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;

public record AtrasoResponse(Long id, boolean atraso, SemanaUtils semana) {
}
