package com.gratiStore.api_gratiStore.infra.adapter.loja.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.VendasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.factory.loja.LojaFactory;
import com.gratiStore.api_gratiStore.infra.adapter.loja.LojaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LojaAdapterImpl implements LojaAdapter {

    private final LojaFactory lojaFactory;

    @Override
    public Loja lojaRequestToLoja(LojaRequest request) {

        return lojaFactory.criar(request.nome(), request.cnpj());
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

    @Override
    public VendasResponse lojaToVendaResponse(Loja loja) {
    
        return new VendasResponse(loja.getTotalVendas());
    }
}
