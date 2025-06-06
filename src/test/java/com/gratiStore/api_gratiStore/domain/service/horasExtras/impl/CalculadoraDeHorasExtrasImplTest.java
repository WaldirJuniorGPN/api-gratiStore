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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraDeHorasExtrasImplTest {

    private final int PRIMEIRA_SEMANA = 1;
    private Atendente atendente;
    private PontoEletronico pontoEletronico;
    private Loja loja;
    private List<PontoEletronico> pontoEletronicoList;
    private CalculadoraDeHorasExtrasImpl calculadora;
    private Map<Integer, List<PontoEletronico>> pontosSemanais;

    @BeforeEach
    void setUp() {
        pontosSemanais = new HashMap<>();
        loja = new Loja("Apple", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(30000));
        calculadora = new CalculadoraDeHorasExtrasImpl();

    }

    @Test
    void deveCalcularCargaHorariaDoDia_quandoHoraSemanalForMenor() {
        pontoEletronico = new PontoEletronico(
                LocalDate.now().minusDays(1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(18,0),
                atendente
        );
        pontoEletronicoList = List.of(pontoEletronico, pontoEletronico);
        pontosSemanais.put(PRIMEIRA_SEMANA, pontoEletronicoList);

        var resultado = calculadora.calcular(pontosSemanais);
        assertEquals(Duration.ofHours(2L), resultado);
    }

    @Test
    void deveCalcularCargaHorariaDaSemana_quandoHoraDiariaForMenor() {
        pontoEletronico = new PontoEletronico(
                LocalDate.now().minusDays(1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17,0),
                atendente
        );

        pontoEletronicoList = List.of(pontoEletronico,
                pontoEletronico,
                pontoEletronico,
                pontoEletronico,
                pontoEletronico,
                pontoEletronico);
        pontosSemanais.put(PRIMEIRA_SEMANA, pontoEletronicoList);

        var resultado = calculadora.calcular(pontosSemanais);
        assertEquals(Duration.ofHours(4), resultado);
    }
}