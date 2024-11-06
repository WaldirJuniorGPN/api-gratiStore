package com.gratiStore.api_gratiStore.controller.dto.request.atendente;

import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

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

        AtrasoStatus atrasoSextaSemana,

        @Min(0)
        Integer quantidadeAtendimentosPrimeiraSemana,

        @Min(0)
        Integer quantidadeAtendimentosSegundaSemana,

        @Min(0)
        Integer quantidadeAtendimentosTerceiraSemana,

        @Min(0)
        Integer quantidadeAtendimentosQuartaSemana,

        @Min(0)
        Integer quantidadeAtendimentosQuintaSemana,

        @Min(0)
        Integer quantidadeAtendimentosSextaSemana
) {
}
