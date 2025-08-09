package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.utils.AtestadoUtils;
import com.gratiStore.api_gratiStore.domain.utils.DescontarEmHorasUtils;
import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;
import com.gratiStore.api_gratiStore.domain.utils.FolgaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgrupadorDePontosPorSemanaImplTest {

    private PontoEletronico ponto1;
    private PontoEletronico ponto2;
    private PontoEletronico ponto3;
    private PontoEletronico ponto4;
    private PontoEletronico ponto5;
    private List<PontoEletronico> pontoEletronicoList;
    private Atendente atendente;
    private AgrupadorDePontosPorSemanaImpl agrupador;

    @BeforeEach
    void setUp() {
        agrupador = new AgrupadorDePontosPorSemanaImpl();
        var loja = new Loja("Google", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(2000));
        ponto1 = new PontoEletronico(LocalDate.of(2025, 5, 1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(19,0),
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendente);

        ponto2 = new PontoEletronico(LocalDate.of(2025, 5, 4),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(19,0),
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendente);

        ponto3 = new PontoEletronico(LocalDate.of(2025, 5, 13),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(19,0),
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendente);

        ponto4 = new PontoEletronico(LocalDate.of(2025, 5, 24),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(19,0),
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendente);

        ponto5 = new PontoEletronico(LocalDate.of(2025, 5, 30),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(19,0),
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendente);

        pontoEletronicoList = List.of(ponto1, ponto2, ponto3, ponto4, ponto5);
    }

    @Test
    void deveCriarUmMapComCincoElementos() {
        var resultado = agrupador.agrupar(pontoEletronicoList);

        assertEquals(5, resultado.size());
        assertTrue(resultado.get(1).contains(ponto1));
    }
}