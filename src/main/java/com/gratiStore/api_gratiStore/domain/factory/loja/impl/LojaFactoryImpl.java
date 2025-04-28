package com.gratiStore.api_gratiStore.domain.factory.loja.impl;

import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.factory.loja.LojaFactory;
import org.springframework.stereotype.Component;

@Component
public class LojaFactoryImpl implements LojaFactory {

    @Override
    public Loja criar(String nome, String cnpj) {
        return new Loja(nome, cnpj);
    }
}
