package com.gratiStore.api_gratiStore.controller.dto.response.atendente;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;

import java.math.BigDecimal;

public record AtendenteResponseVendas(Long id, String nome, BigDecimal vendasPrimeiraSemana,
                                      BigDecimal vendasSegundaSemana, BigDecimal vendasTerceiraSemana,
                                      BigDecimal vendasQuartaSemana, BigDecimal vendasQuintaSemana,
                                      BigDecimal vendasSextaSemana, AtrasoStatus statusPrimeiraSemana,
                                      AtrasoStatus statusSegundaSemana, AtrasoStatus statusTerceiraSemana,
                                      AtrasoStatus statusQuartaSemana, AtrasoStatus statusQuintaSemana,
                                      AtrasoStatus statusSextaSemana) {

    public AtendenteResponseVendas(Atendente atendente) {
        this(atendente.getId(), atendente.getNome(), atendente.getVendasPrimeiraSemana(), atendente.getVendasSegundaSemana(),
                atendente.getVendasTerceiraSemana(), atendente.getVendasQuartaSemana(), atendente.getVendasQuintaSemana(),
                atendente.getVendasSextaSemana(), atendente.getAtrasoStatusPrimeiraSemana(), atendente.getAtrasoStatusSegundaSemana(),
                atendente.getAtrasoStatusTerceiraSemana(), atendente.getAtrasoStatusQuartaSemana(), atendente.getAtrasoStatusQuintaSemana(),
                atendente.getAtrasoStatusSextaSemana());
    }
}
