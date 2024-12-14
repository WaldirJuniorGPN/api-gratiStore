package com.gratiStore.api_gratiStore.domain.service.marca;

import com.gratiStore.api_gratiStore.controller.dto.request.marca.MarcaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.marca.MarcaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MarcaService {

    MarcaResponse criar(MarcaRequest request);

    MarcaResponse atualizar(Long id, MarcaRequest request);

    Page<MarcaResponse> buscarTodos(Pageable pageable);

    MarcaResponse buscar(Long id);

    void deletar(Long id);
}
