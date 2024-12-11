package com.gratiStore.api_gratiStore.infra.adapter.grupo.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.grupo.GrupoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.grupo.GrupoResponse;
import com.gratiStore.api_gratiStore.domain.entities.grupo.Grupo;
import com.gratiStore.api_gratiStore.infra.adapter.grupo.GrupoAdapter;
import org.springframework.stereotype.Component;

@Component
public class GrupoAdapterImpl implements GrupoAdapter {

    @Override
    public Grupo grupoRequestToGrupo(GrupoRequest request) {
        var grupo = new Grupo();
        grupo.setNome(request.nome());

        return grupo;
    }

    @Override
    public GrupoResponse grupoToGrupoResponse(Grupo grupo) {
        return new GrupoResponse(grupo);
    }

    @Override
    public void grupoRequestToGrupo(Grupo grupo, GrupoRequest request) {
        grupo.setNome(request.nome());
    }
}
