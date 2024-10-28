package com.gratiStore.api_gratiStore.infra.adapter.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import org.springframework.stereotype.Component;

@Component
public class AtendenteAdapterImpl implements AtendenteAdapter {

    @Override
    public Atendente atendenteRequestToAtendente(AtendenteRequest request) {
        var atendente = new Atendente();
        atendente.setNome(request.nome());

        return atendente;
    }

    @Override
    public AtendenteResponse atendenteToAtendenteResponse(Atendente atendente) {
        return new AtendenteResponse(atendente);
    }

    @Override
    public Atendente atendenteReqquestToAtendente(Atendente atendente, AtendenteRequest request) {
        atendente.setNome(request.nome());

        return atendente;
    }
}
