package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record AtendenteRequestVendas(
        @DecimalMin("0.00")
        BigDecimal vendasPrimeiraSemana,

        @DecimalMin("0.00")
        BigDecimal vendasSegundaSemana,

        @DecimalMin("0.00")
        BigDecimal vendasTerceiraSemana,

        @DecimalMin("0.00")
        BigDecimal vendasQuartaSemana,

        @DecimalMin("0.00")
        BigDecimal vendasQuintaSemana,

        @DecimalMin("0.00")
        BigDecimal vendasSextaSemana

        //preciso adicionar ENUS para SIM ou NÃO sobre atrasos.
) {
}
