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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                1L);

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

    @Test
    void deveLancarExceptionAoCriarCalculadora_quandoRequestInvalido() {
        assertThrows(IllegalStateException.class, () -> service.criar(null));
    }

    @Test
    void deveAtualizarCalculadoraComSucesso() {
        when(repository.findByIdAndAtivoTrue(any(Long.class))).thenReturn(Optional.of(calculadora));
        when(adapter.calculadoraToCalculadoraResponse(calculadora)).thenReturn(response);
        when(adapter.calculadoraRequestToCalculadora(calculadora, request)).thenReturn(calculadora);

        service.atualizar(1L, request);

        verify(repository, times(1)).findByIdAndAtivoTrue(any(Long.class));
        verify(repository, times(1)).save(calculadora);
        verify(adapter, times(1)).calculadoraRequestToCalculadora(calculadora, request);
        verify(adapter, times(1)).calculadoraToCalculadoraResponse(calculadora);
    }

    @Test
    void deveLancarExceptionAoAtualizar_qundoRequestInvalido() {
        assertThrows(IllegalStateException.class, () -> service.atualizar(1L, null));
    }

    @Test
    void deveLancarExceptionAoAtualizar_quandoIdIZero() {
        assertThrows(IllegalStateException.class, () -> service.atualizar(0L, request));
    }

    @Test
    void deveLancarExceptionAoAtualizar_quandIdNull() {
        assertThrows(IllegalStateException.class, () -> service.atualizar(-1L, request));
    }

    @Test
    void deveRetornarPaginadoTodasAsCalculadorasAtivas() {
        var calculadoraList = List.of(calculadora, calculadora);
        var pageagle = new PageImpl<>(calculadoraList);
        var page = PageRequest.of(1, 10);
        when(repository.findAllByAtivoTrue(page)).thenReturn(Optional.of(pageagle));
        when(adapter.calculadoraToCalculadoraResponse(calculadora)).thenReturn(response);

        service.listarTodos(page);

        verify(repository, times(1)).findAllByAtivoTrue(page);
        verify(adapter, times(calculadoraList.size())).calculadoraToCalculadoraResponse(any(Calculadora.class));
    }

    @Test
    void deveLancarExceptionAoPaginarTodos_quandoPageableForNull() {
        assertThrows(IllegalStateException.class, () -> service.listarTodos(null));
    }

    @Test
    void deveLancarExceptionAoPaginarTodos_quandoTamanhoPaginaMaiorQue100() {
        var pageable = PageRequest.of(0, 101);
        assertThrows(IllegalStateException.class, () -> service.listarTodos(pageable));
    }

    @Test
    void deveFazerExclusaoLogica() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(calculadora));

        service.deletar(1L);

        assertFalse(calculadora.isAtivo());
        verify(repository, times(1)).findByIdAndAtivoTrue(1L);
        verify(repository, times(1)).save(calculadora);
    }

    @Test
    void deveLancarExceptionAoExcluir_quandoIdForNull() {
        assertThrows(IllegalStateException.class, () -> service.deletar(null));
    }

    @Test
    void deveLancarExceptionAoExcluir_quandoIdForZero() {
        assertThrows(IllegalStateException.class, () -> service.deletar(0L));
    }

    @Test
    void deveLancarExceptionAoExcluir_quandoIdForNegativo() {
        assertThrows(IllegalStateException.class, () -> service.deletar(-1L));
    }

    @Test
    void deveBuscarCalculadoraPorId() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(calculadora));
        when(adapter.calculadoraToCalculadoraResponse(calculadora)).thenReturn(response);

        service.buscar(1L);

        verify(repository, times(1)).findByIdAndAtivoTrue(1L);
        verify(adapter, times(1)).calculadoraToCalculadoraResponse(calculadora);
    }

    @Test
    void deveLancarExceptionAoBuscarPorId_quandoIdForNull() {
        assertThrows(IllegalStateException.class, () -> service.buscar(null));
    }

    @Test
    void deveLancarExceptionAoBuscarPorId_quandoIdForZero() {
        assertThrows(IllegalStateException.class, () -> service.buscar(0L));
    }

    @Test
    void deveLancarExceptionAoBuscarPorId_quandoIdForNegativo() {
        assertThrows(IllegalStateException.class, () -> service.buscar(-1L));
    }

    @Test
    void deveListarTodasCalculadorasAtivas() {
        var list = List.of(calculadora, calculadora);
        when(repository.findAllByAtivoTrue()).thenReturn(list);
        when(adapter.calculadoraToCalculadoraResponse(any(Calculadora.class))).thenReturn(response);

        service.listarTodos();

        verify(repository, times(1)).findAllByAtivoTrue();
        verify(adapter, times(list.size())).calculadoraToCalculadoraResponse(any(Calculadora.class));
    }
}