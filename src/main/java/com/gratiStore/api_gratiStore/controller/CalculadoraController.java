package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.service.calculadora.CalculadoraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping
@RestController("/calculadoras")
@RequiredArgsConstructor
public class CalculadoraController {

    private final CalculadoraService service;

    @PostMapping
    public ResponseEntity<CalculadoraResponse> criar(@Valid @RequestBody CalculadoraRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/calculadoras/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalculadoraResponse> atualizar(@PathVariable Long id, @Valid @RequestBody CalculadoraRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalculadoraResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CalculadoraResponse>> listarTodos(Pageable pageable) {
        var response = service.listarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
