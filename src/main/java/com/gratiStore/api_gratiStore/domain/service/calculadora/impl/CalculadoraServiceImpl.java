package com.gratiStore.api_gratiStore.domain.service.calculadora.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.service.calculadora.CalculadoraService;
import com.gratiStore.api_gratiStore.infra.adapter.calculadora.CalculadoraAdapter;
import com.gratiStore.api_gratiStore.infra.repository.CalculadoraRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculadoraServiceImpl implements CalculadoraService {

    private final CalculadoraRepository repository;
    private final CalculadoraAdapter adapter;

    @Override
    @Transactional
    public CalculadoraResponse criar(CalculadoraRequest request) {
        var calculadora = adapter.calculadoraRequestToCalculadora(request);
        repository.save(calculadora);

        return adapter.calculadoraToCalculadoraResponse(calculadora);
    }

    @Override
    @Transactional
    public CalculadoraResponse atualizar(Long id, CalculadoraRequest request) {
        var calculadora = buscarNoBanco(id);
        calculadora = adapter.calculadoraRequestToCalculadora(calculadora, request);
        repository.save(calculadora);

        return adapter.calculadoraToCalculadoraResponse(calculadora);
    }

    @Override
    public CalculadoraResponse buscar(Long id) {
        var calculadora = buscarNoBanco(id);

        return adapter.calculadoraToCalculadoraResponse(calculadora);
    }

    @Override
    public Page<CalculadoraResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).orElseThrow(
                        () -> new IllegalArgumentException("A estrutura de paginação está inválida"))
                .map(adapter::calculadoraToCalculadoraResponse);
    }

    @Override
    public List<CalculadoraResponse> listarTodos() {
        var lojasAtivas = repository.findAllByAtivoTrue();
        if (lojasAtivas.isEmpty()) {
            throw new RuntimeException("Nenhuma calculadora ativa encontrada");
        }

        return lojasAtivas.stream()
                .map(adapter::calculadoraToCalculadoraResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var calculadora = buscarNoBanco(id);
        calculadora.setAtivo(false);
        repository.save(calculadora);
    }

    private Calculadora buscarNoBanco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Calculadora com ID: %d não existe ou não está ativa", id)));
    }
}
