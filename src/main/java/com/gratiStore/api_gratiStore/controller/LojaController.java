package com.gratiStore.api_gratiStore.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.VendasResponse;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/lojas")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService service;

    @PostMapping
    public ResponseEntity<LojaResponse> criar(@Valid @RequestBody LojaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/lojas/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody LojaRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<LojaResponse> buscar(@PathVariable String cnpj) {
        var response = service.buscar(cnpj);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<LojaResponse>> listarTodos(Pageable pageable) {
        var response = service.listarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<LojaResponse>> listarTodos() {
        var response = service.lsitarTodos();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/atendentes")
    public ResponseEntity<List<AtendenteResponse>> listarAtendentesPorLoja(@PathVariable Long id) {
        var response = service.listarAtendentesPorLoja(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/vendas")
    public ResponseEntity<VendasResponse> buscaVendasTotais(@PathVariable Long id) {
        var response = service.buscarVendasTotais(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{lojaId}")
    public ResponseEntity<Void> zerarValoresAtendentes(@PathVariable Long lojaId) {
        service.zerarValoresAtendentes(lojaId);

        return ResponseEntity.noContent().build();
    }
}
