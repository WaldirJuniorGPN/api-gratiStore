package com.gratiStore.api_gratiStore.infra.adapter.loja.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.infra.adapter.loja.LojaAdapter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<AtendenteResponse> mapAtendentesToAtendenteResponse(List<Atendente> atendentes) {
        return atendentes.stream()
                .map(AtendenteResponse::new)
                .sorted(Comparator.comparing(AtendenteResponse::nome))
                .collect(Collectors.toList());
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
