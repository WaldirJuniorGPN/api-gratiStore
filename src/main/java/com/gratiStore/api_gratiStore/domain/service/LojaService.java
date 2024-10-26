package com.gratiStore.api_gratiStore.domain.service;

import com.gratiStore.api_gratiStore.controller.dto.request.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.LojaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LojaService {

    LojaResponse criar(LojaRequest request);

    LojaResponse atualizar(Long id, LojaRequest request);

    LojaResponse buscar(Long id);

    Page<LojaResponse> listarTodos(Pageable pageable);

    void deletar(Long id);
}
