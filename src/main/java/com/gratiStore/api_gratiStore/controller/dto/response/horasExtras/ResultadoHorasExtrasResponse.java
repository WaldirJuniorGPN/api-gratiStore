package com.gratiStore.api_gratiStore.controller.dto.response.horasExtras;

import java.math.BigDecimal;
import java.time.Duration;

public record ResultadoHorasExtrasResponse(String nomeAtendente,
                                           Integer mes,
                                           Integer ano,
                                           BigDecimal valorAReceber,
                                           Duration totalHorasExtras) {
}
