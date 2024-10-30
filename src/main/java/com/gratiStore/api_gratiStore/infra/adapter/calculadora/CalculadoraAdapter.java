package com.gratiStore.api_gratiStore.infra.adapter.calculadora;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;

public interface CalculadoraAdapter {

    Calculadora calculadoraRequestToCalculadora(CalculadoraRequest request);

    CalculadoraResponse calculadoraToCalculadoraResponse(Calculadora calculadora);

    Calculadora calculadoraRequestToCalculadora(Calculadora calculadora, CalculadoraRequest request);
}
