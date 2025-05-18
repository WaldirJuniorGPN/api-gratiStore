package com.gratiStore.api_gratiStore.domain.service.calculadora.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.calculadora.CalculadoraResponse;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.infra.adapter.calculadora.CalculadoraAdapter;
import com.gratiStore.api_gratiStore.infra.repository.CalculadoraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculadoraServiceImplTest {

    @Mock
    private CalculadoraRepository repository;

    @Mock
    private CalculadoraAdapter adapter;

    @InjectMocks
    private CalculadoraServiceImpl service;

    private Calculadora calculadora;
    private CalculadoraRequest request;
    private CalculadoraResponse response;

    @BeforeEach
    void setUp() {
        Loja loja = new Loja("Google", "06026378000140");
        calculadora = new Calculadora("Calculadora",
                10.0,
                5.0,
                3.0,
                1.0,
                BigDecimal.valueOf(40),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(10),
                loja);

        request = new CalculadoraRequest(
                "Calculadora",
                10.0,
                5.0,
                3.0,
                1.0,
                BigDecimal.valueOf(40),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(10),
                1L
        );

        response = new CalculadoraResponse(1L,
                "Calculadora",
                10.0,
                5.0,
                3.0,
                1.0,
                BigDecimal.valueOf(40),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(10),
                1L);
    }

    @Test
    void deveCriarCalculadora() {
        when(adapter.calculadoraRequestToCalculadora(request)).thenReturn(calculadora);
        when(adapter.calculadoraToCalculadoraResponse(calculadora)).thenReturn(response);

        service.criar(request);

        verify(repository, times(1)).save(calculadora);
        verify(adapter, times(1)).calculadoraRequestToCalculadora(request);
        verify(adapter, times(1)).calculadoraToCalculadoraResponse(calculadora);
    }
}