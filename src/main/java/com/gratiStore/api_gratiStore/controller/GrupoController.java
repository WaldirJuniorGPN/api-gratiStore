package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.grupo.GrupoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.grupo.GrupoResponse;
import com.gratiStore.api_gratiStore.domain.service.grupo.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService service;

    @PostMapping
    public ResponseEntity<GrupoResponse> criar(@RequestBody @Valid GrupoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/grupos/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponse> atualizar(@PathVariable Long id, GrupoRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<GrupoResponse>> buscarTodos(Pageable pageable) {
        var response = service.buscarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}

