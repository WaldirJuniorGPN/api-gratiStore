package com.gratiStore.api_gratiStore.domain.service.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import com.gratiStore.api_gratiStore.infra.repository.AtendenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtendenteServiceImplTest {

    @InjectMocks
    private AtendenteServiceImpl atendenteService;

    @Mock
    private LojaService lojaService;

    @Mock
    private AtendenteAdapter adapter;

    @Mock
    private AtendenteRepository repository;

    private Atendente atendenteMock;
    private AtendenteRequest request;
    private AtendenteResponse response;

    @BeforeEach
    void setUp() {
        var loja = new Loja("Americanas", "06026378000140");
        this.atendenteMock = new Atendente("Fulano", loja, new BigDecimal("2000"));
        this.request = new AtendenteRequest("Fulano", 1L, new BigDecimal("2000"));
        this.response = new AtendenteResponse(1L, "Fulano", "Americana");
    }

    @Test
    void deveCriarAtendente_quandoRequestValido() {
        when(adapter.atendenteRequestToAtendente(request)).thenReturn(atendenteMock);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteToAtendenteResponse(atendenteMock)).thenReturn(response);

        var resultado = atendenteService.criar(request);

        assertEquals("Fulano", resultado.nome());
        verify(adapter, times(1)).atendenteRequestToAtendente(request);
        verify(repository, times(1)).save(any(Atendente.class));
        verify(adapter, times(1)).atendenteToAtendenteResponse(any(Atendente.class));
    }
}