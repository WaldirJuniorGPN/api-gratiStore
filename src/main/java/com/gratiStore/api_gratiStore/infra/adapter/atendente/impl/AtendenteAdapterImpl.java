package com.gratiStore.api_gratiStore.infra.adapter.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtrasoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.*;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.factory.atendente.AtendenteFactory;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtendenteAdapterImpl implements AtendenteAdapter {

    private final AtendenteFactory atendenteFactory;
    private final LojaService lojaService;

    @Override
    public Atendente atendenteRequestToAtendente(AtendenteRequest request) {
        var atendente = atendenteFactory.criar(request);

        return atendente;
    }

    @Override
    public Atendente atendenteRequestToAtendente(AtendenteRequestPlanilha request) {

        return atendenteFactory.criar(request);
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
        atendente.setSalario(request.salario());

        atribuirAtendenteALoja(atendente, loja);

        return atendente;
    }

    @Override
    public AtendenteResponseVendas atendenteToAtendenteResponseVendas(Atendente atendente) {
        return new AtendenteResponseVendas(atendente);
    }

    @Override
    public AtrasoResponse atendenteToAtrasoResponse(Atendente atendente, AtrasoRequest request) {
        return new AtrasoResponse(atendente.getId(), request.atraso(), request.semana());
    }

    @Override
    public UpdateSalarioResponse atendentetoUpdateSalarioResponse(Atendente atendente) {
        return new UpdateSalarioResponse(atendente.getId(), atendente.getSalario());
    }

    @Override
    public SalarioAtendenteResponse atendenteToSalarioAtendenteResponse(Atendente atendente) {
        return new SalarioAtendenteResponse(atendente.getNome(), atendente.getSalario());
    }

    private void atribuirAtendenteALoja(Atendente atendente, Loja loja) {
        if (!loja.getAtendentes().contains(atendente)) {
            loja.getAtendentes().add(atendente);
        }
    }
}
