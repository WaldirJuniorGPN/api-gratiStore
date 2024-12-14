package com.gratiStore.api_gratiStore.domain.service.marca.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.marca.MarcaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.marca.MarcaResponse;
import com.gratiStore.api_gratiStore.domain.entities.marca.Marca;
import com.gratiStore.api_gratiStore.domain.service.marca.MarcaService;
import com.gratiStore.api_gratiStore.infra.adapter.marca.MarcaAdapter;
import com.gratiStore.api_gratiStore.infra.repository.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository repository;
    private final MarcaAdapter adapter;

    @Override
    @Transactional
    public MarcaResponse criar(MarcaRequest request) {
        var marca = adapter.marcaRequestToMarca(request);
        repository.save(marca);

        return adapter.marcaToMarcaResponse(marca);
    }

    @Override
    @Transactional
    public MarcaResponse atualizar(Long id, MarcaRequest request) {
        var marca = buscarNoBranco(id);
        adapter.marcaRequestToMarca(marca, request);

        return adapter.marcaToMarcaResponse(marca);
    }

    @Override
    public Page<MarcaResponse> buscarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).orElseThrow(
                        () -> new IllegalArgumentException("A estrutura de paginação está inválida"))
                .map(adapter::marcaToMarcaResponse);

    }

    @Override
    public MarcaResponse buscar(Long id) {
        var marca = buscarNoBranco(id);

        return adapter.marcaToMarcaResponse(marca);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var marca = buscarNoBranco(id);
        marca.setAtivo(false);
    }

    private Marca buscarNoBranco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Marca de ID: %d não foi encontrada ou não está ativa", id)));
    }
}
