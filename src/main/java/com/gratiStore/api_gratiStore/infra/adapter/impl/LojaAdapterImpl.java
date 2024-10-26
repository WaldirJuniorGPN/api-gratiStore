package com.gratiStore.api_gratiStore.infra.adapter.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.Loja;
import com.gratiStore.api_gratiStore.infra.adapter.LojaAdapter;
import org.springframework.stereotype.Component;

@Component
public class LojaAdapterImpl implements LojaAdapter {

    @Override
    public Loja lojaRequestToLoja(LojaRequest request) {
        var loja = new Loja();
        loja.setNome(request.nome());
        loja.setCnpj(normalizaCnpj(request.cnpj()));

        return loja;
    }

    @Override
    public Loja lojaRequestToLoja(Loja loja, LojaRequest request) {
        loja.setNome(request.nome());
        loja.setCnpj(normalizaCnpj(request.cnpj()));

        return loja;
    }

    @Override
    public LojaResponse lojaToLojaResponse(Loja loja) {
        return new LojaResponse(loja);
    }

    private String normalizaCnpj(String cnpj) {
        if (cnpj == null) {
            return null;
        }

        return cnpj.replaceAll("\\D", "");
    }
}
