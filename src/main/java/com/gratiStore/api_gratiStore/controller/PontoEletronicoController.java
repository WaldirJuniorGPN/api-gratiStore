package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ponto")
@RestController
@RequiredArgsConstructor
public class PontoEletronicoController {

    private PontoEletronicoService service;

    public ResponseEntity<Void> registrarPonto(@RequestBody @Valid PontoRequest request) {
        service.registrarPronto(request);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<HistoricoResponse>> listarHistorico(Pageable pageable) {
        var page = service.listarHistorico(pageable);

        return ResponseEntity.ok(page);
    }
}
