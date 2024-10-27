package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.LojaResponse;
import com.gratiStore.api_gratiStore.domain.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
