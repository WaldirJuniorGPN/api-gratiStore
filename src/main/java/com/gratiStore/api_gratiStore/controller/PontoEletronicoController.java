package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> registrarPonto(@RequestBody @Valid PontoRequest request) {
        service.registrarPronto(request);

        return ResponseEntity.noContent().build();
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
}
