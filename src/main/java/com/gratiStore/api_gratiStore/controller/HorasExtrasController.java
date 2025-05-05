package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResumoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.HorasExtrasService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("horas-extras")
@RestController
@RequiredArgsConstructor
public class HorasExtrasController {

    private final HorasExtrasService service;

    @Operation(
            summary = "Calcula Horas Extras dos Atendentes",
            description = "Busca todos os atendentes ativos de uma determinada loja, faz o cálculo de horas extras, e " +
                    "retorna a lista resumida."
    )
    @PostMapping("/calcular")
    public ResponseEntity<List<ResumoHorasExtrasResponse>> calcular(@Valid @RequestBody FiltroHorasExtrasRequest request) {

        var horasExtras = service.calcular(request);

        return ResponseEntity.ok(horasExtras);
    }
}
