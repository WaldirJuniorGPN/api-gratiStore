package com.gratiStore.api_gratiStore.controller.dto.response.atendente;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;

public record AtendenteResponse(Long id, String nome, String nomeLoja) {
    public AtendenteResponse(Atendente atendente) {
        this(atendente.getId(), atendente.getNome(), atendente.getLoja().getNome());
    }
}
