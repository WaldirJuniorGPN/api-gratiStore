package com.gratiStore.api_gratiStore.domain.factory.loja;

import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;

public interface LojaFactory {

    Loja criar(String nome, String cnpj);
}
