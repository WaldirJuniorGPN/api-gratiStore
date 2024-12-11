package com.gratiStore.api_gratiStore.controller.dto.response.grupo;

import com.gratiStore.api_gratiStore.domain.entities.grupo.Grupo;

public record GrupoResponse(Long id, String nome) {

    public GrupoResponse(Grupo grupo) {
        this(grupo.getId(), grupo.getNome());
    }
}
