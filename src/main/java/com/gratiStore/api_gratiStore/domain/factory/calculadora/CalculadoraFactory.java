package com.gratiStore.api_gratiStore.domain.factory.calculadora;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;

public interface CalculadoraFactory {

    Calculadora criar(CalculadoraRequest request);
}
