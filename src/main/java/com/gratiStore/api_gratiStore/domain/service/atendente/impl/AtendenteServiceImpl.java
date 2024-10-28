package com.gratiStore.api_gratiStore.domain.service.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import com.gratiStore.api_gratiStore.infra.repository.AtendenteRepository;
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
public class AtendenteServiceImpl implements AtendenteService {

    private final AtendenteRepository repository;
    private final AtendenteAdapter adapter;

    @Override
    @Transactional
    public AtendenteResponse criar(AtendenteRequest request) {
        var atendente = adapter.atendenteRequestToAtendente(request);
        repository.save(atendente);

        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    @Transactional
    public AtendenteResponse atualizar(Long id, AtendenteRequest request) {
        var atendente = buscarNoBanco(id);
        adapter.atendenteReqquestToAtendente(atendente, request);
        repository.save(atendente);

        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    public AtendenteResponse buscar(Long id) {
        var atendente = buscarNoBanco(id);

        return adapter.atendenteToAtendenteResponse(atendente);
    }

    @Override
    public Page<AtendenteResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).orElseThrow(
                        () -> new IllegalArgumentException("A estrutura de paginação está inválida"))
                .map(adapter::atendenteToAtendenteResponse);
    }

    @Override
    public List<AtendenteResponse> listarTodos() {
        var atendentesAtivos = repository.findAllByAtivoTrue();
        if (atendentesAtivos.isEmpty()) {
            throw new RuntimeException("Nenhum atendente ativo encontrado");
        }

        return atendentesAtivos.stream()
                .map(adapter::atendenteToAtendenteResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var atendente = buscarNoBanco(id);
        atendente.setAtivo(false);
        repository.save(atendente);
    }

    private Atendente buscarNoBanco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Atendente com ID: %d não foi encontrado ou não está ativo", id)));
    }
}
