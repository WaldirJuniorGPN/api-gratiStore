package com.gratiStore.api_gratiStore.domain.service.atendente;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AtendenteService {

    AtendenteResponse criar(AtendenteRequest request);

    AtendenteResponse atualizar(Long id, AtendenteRequest request);

    AtendenteResponse buscar(Long id);

    Page<AtendenteResponse> listarTodos(Pageable pageable);

    List<AtendenteResponse> listarTodos();

    void deletar(Long id);

    AtendenteResponse converteAtendenteToAtendenteResponse(Atendente atendente);
}
