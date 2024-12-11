package com.gratiStore.api_gratiStore.domain.service.grupo;

import com.gratiStore.api_gratiStore.controller.dto.request.grupo.GrupoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.grupo.GrupoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrupoService {

    GrupoResponse criar(GrupoRequest request);

    GrupoResponse atualizar(Long id, GrupoRequest request);

    GrupoResponse buscar(Long id);

    Page<GrupoResponse> buscarTodos(Pageable pageable);

    void deletar(Long id);
}
