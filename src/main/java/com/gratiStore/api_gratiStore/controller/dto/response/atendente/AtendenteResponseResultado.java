package com.gratiStore.api_gratiStore.controller.dto.response.atendente;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

import java.math.BigDecimal;

public record AtendenteResponseResultado(Long id, String nome, BigDecimal vendasTotais, BigDecimal gratificacao,
                                         BigDecimal bonus) {

    public AtendenteResponseResultado(Atendente atendente) {
        this(atendente.getId(), atendente.getNome(), atendente.getTotalVendas(), atendente.getGratificacao(),
                atendente.getBonus());
    }
}
