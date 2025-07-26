package com.gratiStore.api_gratiStore.controller.dto.response.atendente;

import java.math.BigDecimal;

public record UpdateSalarioResponse(Long idAtendente, BigDecimal salario) {
}
