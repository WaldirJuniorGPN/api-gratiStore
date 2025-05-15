package com.gratiStore.api_gratiStore.controller.dto.response.atendente;

import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;

public record AtrasoResponse(Long id, AtrasoStatus status, SemanaUtils semana) {
}
