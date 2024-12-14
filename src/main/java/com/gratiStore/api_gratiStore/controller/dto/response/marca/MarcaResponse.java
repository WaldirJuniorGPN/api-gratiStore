package com.gratiStore.api_gratiStore.controller.dto.response.marca;

import com.gratiStore.api_gratiStore.domain.entities.marca.Marca;

public record MarcaResponse(Long id, String nome) {

    public MarcaResponse(Marca marca) {
        this(marca.getId(), marca.getNome());
    }
}
