package com.gratiStore.api_gratiStore.controller.dto.response.horasExtras;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gratiStore.api_gratiStore.controller.dto.serializer.DurationSerializer;

import java.math.BigDecimal;
import java.time.Duration;

public record ResultadoHorasExtrasResponse(String nomeAtendente,
                                           Integer mes,
                                           Integer ano,
                                           BigDecimal valorAReceber50PorCento,
                                           BigDecimal valorAReceber100PorCento,
                                           @JsonSerialize(using = DurationSerializer.class)
                                           Duration totalHorasExtras) {
}
