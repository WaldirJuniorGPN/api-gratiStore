package com.gratiStore.api_gratiStore.domain.service.loja.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.loja.LojaAdapter;
import com.gratiStore.api_gratiStore.infra.repository.LojaRepository;
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
public class LojaServiceImpl implements LojaService {

    private final LojaAdapter adapter;
    private final LojaRepository repository;

    @Override
    @Transactional
    public LojaResponse criar(LojaRequest request) {
        var loja = adapter.lojaRequestToLoja(request);
        repository.save(loja);

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    @Transactional
    public LojaResponse atualizar(Long id, LojaRequest request) {
        var loja = buscarNoBanco(id);
        adapter.lojaRequestToLoja(loja, request);
        repository.save(loja);

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    public LojaResponse buscar(Long id) {
        var loja = buscarNoBanco(id);

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    public LojaResponse buscar(String cnpj) {
        var loja = repository.findByCnpjAndAtivoTrue(cnpj).orElseThrow(
                () -> new EntityNotFoundException(String.format("Loja com CNPJ: %d não foi encontrada ou não está ativa", cnpj)));

        return adapter.lojaToLojaResponse(loja);
    }

    @Override
    public Page<LojaResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new IllegalArgumentException("A estrutura de paginação está inválida")).map(adapter::lojaToLojaResponse);
    }

    @Override
    public List<LojaResponse> lsitarTodos() {
        var lojasAtivas = repository.findAllByAtivoTrue();
        if (lojasAtivas.isEmpty()) {
            throw new RuntimeException("Nenhuma loja ativa encontrada");
        }

        return lojasAtivas.stream()
                .map(adapter::lojaToLojaResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var loja = buscarNoBanco(id);
        loja.setAtivo(false);
        repository.save(loja);
    }

    private Loja buscarNoBanco(Long id) {
        return repository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Loja com ID: %d não foi encontrada ou não está ativa", id)));
    }
}
