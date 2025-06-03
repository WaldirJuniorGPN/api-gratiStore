package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraDeHorasExtrasImplTest {

    private Atendente atendente;
    private PontoEletronico pontoEletronico;
    private Loja loja;
    private List<PontoEletronico> pontoEletronicoList;
    private CalculadoraDeHorasExtrasImpl calculadora;

    @BeforeEach
    void setUp() {
        loja = new Loja("Apple", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(30000));
        pontoEletronico = new PontoEletronico(
                LocalDate.now().minusDays(1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(18,0),
                atendente
        );
        pontoEletronicoList = List.of(pontoEletronico, pontoEletronico);
        calculadora = new CalculadoraDeHorasExtrasImpl();
    }

    @Test
    void deveCalcularCargaHorariaDoDia() {
        var resultado = calculadora.calcular(pontoEletronicoList);
        assertEquals(Duration.ofHours(2L), resultado);
    }

    @Test
    void deveRetornarZero_quandoListaDePontoForVazia() {
        var lista = new ArrayList<PontoEletronico>();
        var resultado = calculadora.calcular(lista);
        assertEquals(Duration.ZERO, resultado);
    }

    @Test
    void deveInterromperFluxo_quandoListaForNull() {
        assertThrows(IllegalStateException.class, () -> calculadora.calcular(null));
    }
}