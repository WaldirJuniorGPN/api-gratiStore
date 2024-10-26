package com.gratiStore.api_gratiStore.controller.dto.response;

import com.gratiStore.api_gratiStore.domain.entities.Loja;

public record LojaResponse(Long id, String nome, String cnpj) {

    public LojaResponse(Loja loja) {
        this(loja.getId(), loja.getNome(), loja.getCnpj());
    }
}
