package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.service.calculadora.CalculadoraService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/calculadoras")
@RestController
@RequiredArgsConstructor
public class CalculadoraController {

    private final CalculadoraService service;

    @Operation(
            summary = "Criar nova Calculadora",
            description = "Registra uma nova calculadora com os dados fornecidos na requisição."
    )
    @PostMapping
    public ResponseEntity<CalculadoraResponse> criar(@Valid @RequestBody CalculadoraRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/calculadoras/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(
            summary = "Atualizar Calculadora existente",
            description = "Atualiza os dados de uma calculadora ativa com base no ID informado."
    )
    @PutMapping("/{id}")
    public ResponseEntity<CalculadoraResponse> atualizar(@PathVariable Long id, @Valid @RequestBody CalculadoraRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar Calculadora por ID",
            description = "Retorna os dados de uma calculadora ativa com base no ID informado."
    )
    @GetMapping("/{id}")
    public ResponseEntity<CalculadoraResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar Calculadoras ativas (paginado)",
            description = "Retorna uma lista paginada contendo as calculadoras com status ativo."
    )
    @GetMapping
    public ResponseEntity<Page<CalculadoraResponse>> listarTodos(Pageable pageable) {
        var response = service.listarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar todas as Calculadoras ativas (não paginado)",
            description = "Retorna todas as calculadoras ativas em formato de lista simples."
    )
    @GetMapping("/listar")
    public ResponseEntity<List<CalculadoraResponse>> listarTodos() {
        var response = service.listarTodos();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Desativar Calculadora",
            description = "Realiza a deleção lógica da calculadora, alterando seu status para inativo."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
