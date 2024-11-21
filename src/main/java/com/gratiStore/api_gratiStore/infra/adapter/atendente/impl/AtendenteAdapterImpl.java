package com.gratiStore.api_gratiStore.infra.adapter.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponseVendas;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtendenteAdapterImpl implements AtendenteAdapter {

    private final LojaService lojaService;

    @Override
    public Atendente atendenteRequestToAtendente(AtendenteRequest request) {
        var atendente = new Atendente();
        var loja = lojaService.buscarLoja(request.lojaId());
        atendente.setNome(request.nome());
        atendente.setLoja(loja);

        loja.getAtendentes().add(atendente);

        return atendente;
    }

    @Override
    public Atendente atendenteRequestToAtendente(AtendenteRequestPlanilha request) {
        var atendente = new Atendente();
        var loja = lojaService.buscarLoja(request.lojaId());
        atendente.setNome(request.nome());
        atendente.setLoja(loja);

        return atendente;
    }

    @Override
    public AtendenteResponse atendenteToAtendenteResponse(Atendente atendente) {
        return new AtendenteResponse(atendente);
    }

    @Override
    public Atendente atendenteRequestToAtendente(Atendente atendente, AtendenteRequest request) {
        var loja = lojaService.buscarLoja(request.lojaId());
        atendente.setNome(request.nome());
        atendente.setLoja(loja);

        atribuirAtendenteALoja(atendente, loja);

        return atendente;
    }

    @Override
    public AtendenteResponseVendas atendenteToAtendenteResponseVendas(Atendente atendente) {
        return new AtendenteResponseVendas(atendente);
    }

    private void atribuirAtendenteALoja(Atendente atendente, Loja loja) {
        if (!loja.getAtendentes().contains(atendente)) {
            loja.getAtendentes().add(atendente);
        }
    }
}
