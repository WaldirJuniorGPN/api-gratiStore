package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record AtendenteRequestVendas(
        @DecimalMin("0.00")
        BigDecimal vendasPrimeiraSemana,

        AtrasoStatus atrasoPrimeiraSemana,

        @DecimalMin("0.00")
        BigDecimal vendasSegundaSemana,

        AtrasoStatus atrasoSegundaSemana,

        @DecimalMin("0.00")
        BigDecimal vendasTerceiraSemana,

        AtrasoStatus atrasoTerceiraSemana,

        @DecimalMin("0.00")
        BigDecimal vendasQuartaSemana,

        AtrasoStatus atrasoQuartaSemana,

        @DecimalMin("0.00")
        BigDecimal vendasQuintaSemana,

        AtrasoStatus atrasoQuintaSemana,

        @DecimalMin("0.00")
        BigDecimal vendasSextaSemana,

        AtrasoStatus atrasoSextaSemana
) {
}
