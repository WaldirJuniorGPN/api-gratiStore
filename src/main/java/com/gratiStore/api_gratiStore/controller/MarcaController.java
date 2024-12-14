package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.marca.MarcaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.marca.MarcaResponse;
import com.gratiStore.api_gratiStore.domain.service.marca.MarcaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/marcas")
@RestController
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService service;

    @PostMapping
    public ResponseEntity<MarcaResponse> criar(@Valid @RequestBody MarcaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/marcas/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> atualizar(@PathVariable Long id, @Valid MarcaRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<MarcaResponse>> listarTodos(Pageable pageable) {
        var page = service.buscarTodos(pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
