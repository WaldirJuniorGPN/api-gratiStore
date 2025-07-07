package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.PontoResponse;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/ponto")
@RestController
@RequiredArgsConstructor
public class PontoEletronicoController {

    private final PontoEletronicoService service;

    @Operation(
            summary = "Registrar Ponto Eletrônico",
            description = "Registra um novo ponto eletrônico para um atendente com base nos dados fornecidos na requisição."
    )
    @PostMapping
    public ResponseEntity<PontoResponse> registrarPonto(@RequestBody @Valid PontoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.registrarPonto(request);
        var uri = uriComponentsBuilder.path("/ponto/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(
            summary = "Listar Histórico de Pontos",
            description = "Retorna uma lista paginada contendo o histórico de pontos eletrônicos registrados."
    )
    @GetMapping
    public ResponseEntity<Page<HistoricoResponse>> listarHistorico(Pageable pageable) {
        var page = service.listarHistorico(pageable);

        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar Ponto Eletrônico por ID",
            description = "Retorna um Ponto Eletrônico com base no ID fornecido."
    )
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponse> buscarPonto(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar Ponto Eletrônico",
            description = "Atualiza na base os dados de um Ponto Eletrônico. Todos os parâmetros são atualizados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoResponse> atualizarPonto(@PathVariable Long id, @RequestBody @Valid PontoRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deletar Ponto Eletrônico",
            description = "Faz a deleção física de um Ponto Eletrônico."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPonto(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
