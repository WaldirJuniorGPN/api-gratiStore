package com.gratiStore.api_gratiStore.domain.service.grupo.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.grupo.GrupoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.grupo.GrupoResponse;
import com.gratiStore.api_gratiStore.domain.entities.grupo.Grupo;
import com.gratiStore.api_gratiStore.domain.service.grupo.GrupoService;
import com.gratiStore.api_gratiStore.infra.adapter.grupo.GrupoAdapter;
import com.gratiStore.api_gratiStore.infra.repository.GrupoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository repository;
    private final GrupoAdapter adapter;

    @Override
    public GrupoResponse criar(GrupoRequest request) {
        var grupo = adapter.grupoRequestToGrupo(request);
        repository.save(grupo);

        return adapter.grupoToGrupoResponse(grupo);
    }

    @Override
    public GrupoResponse atualizar(Long id, GrupoRequest request) {
        var grupo = buscarNoBanco(id);
        adapter.grupoRequestToGrupo(grupo, request);

        return adapter.grupoToGrupoResponse(grupo);
    }

    @Override
    public GrupoResponse buscar(Long id) {
        var grupo = buscarNoBanco(id);

        return adapter.grupoToGrupoResponse(grupo);
    }

    @Override
    public Page<GrupoResponse> buscarTodos(Pageable pageable) {
        return repository.findAllEndAtivoTrue(pageable).orElseThrow(
                        () -> new IllegalArgumentException("A estrutura de paginação está inválida"))
                .map(adapter::grupoToGrupoResponse);
    }

    @Override
    public void deletar(Long id) {
        var grupo = buscarNoBanco(id);
        grupo.setAtivo(false);
        grupo.setNome(String.format("%s - Excluído - %s", grupo.getNome(), LocalDateTime.now()));
    }

    private Grupo buscarNoBanco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Grupo de ID: %d não foi encontrado ou não está ativo", id))
        );
    }
}
