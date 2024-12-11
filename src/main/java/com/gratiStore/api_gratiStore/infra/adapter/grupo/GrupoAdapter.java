package com.gratiStore.api_gratiStore.infra.adapter.grupo;

import com.gratiStore.api_gratiStore.controller.dto.request.grupo.GrupoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.grupo.GrupoResponse;
import com.gratiStore.api_gratiStore.domain.entities.grupo.Grupo;

import java.util.Optional;

public interface GrupoAdapter {

    Grupo grupoRequestToGrupo(GrupoRequest request);

    GrupoResponse grupoToGrupoResponse(Grupo grupo);

    void grupoRequestToGrupo(Grupo grupo, GrupoRequest request);
}
