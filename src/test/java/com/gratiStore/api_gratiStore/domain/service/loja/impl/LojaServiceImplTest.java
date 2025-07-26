package com.gratiStore.api_gratiStore.domain.service.loja.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.infra.adapter.loja.LojaAdapter;
import com.gratiStore.api_gratiStore.infra.repository.LojaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LojaServiceImplTest {

    @Mock
    private LojaAdapter adapter;

    @Mock
    private LojaRepository repository;

    @InjectMocks
    private LojaServiceImpl service;

    private LojaRequest request;
    private LojaResponse response;
    private Loja loja;

    @BeforeEach
    void setUp() {
        request = new LojaRequest("Strategy", "06026378000140");
        response = new LojaResponse(1L, "Strategy", "06026378000140");
        loja = new Loja("Strategy", "06026378000140");
    }

    @Test
    void deveCriarLojaComSucesso_quandoRequestValido() {
        when(adapter.lojaRequestToLoja(request)).thenReturn(loja);
        when(adapter.lojaToLojaResponse(loja)).thenReturn(response);

        service.criar(request);

        verify(adapter, times(1)).lojaRequestToLoja(request);
        verify(adapter, times(1)).lojaToLojaResponse(loja);
    }

    @Test
    void deveFalharAoCriarLoja_quandoNomeForNull() {
        assertThrows(IllegalStateException.class, () -> service.criar(null));
    }

    @Test
    void deveAtualizarComSucesso() {
        when(adapter.lojaToLojaResponse(loja)).thenReturn(response);
        when(repository.findByIdAndAtivoTrue(any(Long.class))).thenReturn(Optional.of(loja));
        var novoRequest = new LojaRequest("Google", "93545716000155");

        service.atualizar(1L, novoRequest);

        assertEquals(novoRequest.cnpj(), loja.getCnpj().value());
        assertEquals(novoRequest.nome(), loja.getNome());
        verify(adapter, times(1)).lojaToLojaResponse(loja);
        verify(repository, times(1)).findByIdAndAtivoTrue(1L);
    }

    @Test
    void deveFalharAoAtualizar_quandoIdForNull() {
        assertThrows(IllegalStateException.class, () -> service.atualizar(null, request));
    }

    @Test
    void deveFalharAoAtualizar_quandoIdForZero() {
        assertThrows(IllegalStateException.class, () -> service.atualizar(0L, request));
    }

    @Test
    void deveFalharAoAtualizar_quandoIdForNegativo() {
        assertThrows(IllegalStateException.class, () -> service.atualizar(-1L, request));
    }

    @Test
    void deveBuscarLojaComSucesso_quandoIdValido() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(loja));
        when(adapter.lojaToLojaResponse(loja)).thenReturn(response);

        service.buscar(1L);

        verify(repository, times(1)).findByIdAndAtivoTrue(1L);
        verify(adapter, times(1)).lojaToLojaResponse(loja);
    }

    @Test
    void deveFalharAoBuscarLoja_quandoIdForNull() {
        Long id = null;
        assertThrows(IllegalStateException.class, () -> service.buscar(id));
    }

    @Test
    void deveFalharAoBuscarLoja_quandoIdForZero() {
        assertThrows(IllegalStateException.class, () -> service.buscar(0L));
    }

    @Test
    void deveFalharAoBuscarLoja_quandoIdForNegativo() {
        assertThrows(IllegalStateException.class, () -> service.buscar(-1L));
    }
}