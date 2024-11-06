package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponseResultado;
import com.gratiStore.api_gratiStore.domain.service.resultado.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resultados")
@RequiredArgsConstructor
public class ResultadoController {

    private final ResultadoService service;

    @PostMapping
    public ResponseEntity<List<LojaResponseResultado>> calcularResultado() {
        var response = service.calcularResultado();

        return ResponseEntity.ok(response);
    }
}
