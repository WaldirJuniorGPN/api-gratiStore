package com.gratiStore.api_gratiStore.controller.dto.response.horasExtras;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

import java.time.Duration;

public record ResumoHorasExtrasResponse(Long atendenteId, Duration totalHorasExtras) {
}
