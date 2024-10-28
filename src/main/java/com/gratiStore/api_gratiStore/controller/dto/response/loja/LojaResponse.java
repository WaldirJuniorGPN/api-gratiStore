package com.gratiStore.api_gratiStore.controller.dto.response.loja;

import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;

public record LojaResponse(Long id, String nome, String cnpj) {

    public LojaResponse(Loja loja) {
        this(loja.getId(), loja.getNome(), loja.getCnpj());
    }
}
