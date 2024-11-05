package com.gratiStore.api_gratiStore.domain.service.calculadora;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CalculadoraService {

    CalculadoraResponse criar(CalculadoraRequest request);

    CalculadoraResponse atualizar(Long id, CalculadoraRequest request);

    CalculadoraResponse buscar(Long id);

    Page<CalculadoraResponse> listarTodos(Pageable pageable);

    List<CalculadoraResponse> listarTodos();

    void deletar(Long id);
}
