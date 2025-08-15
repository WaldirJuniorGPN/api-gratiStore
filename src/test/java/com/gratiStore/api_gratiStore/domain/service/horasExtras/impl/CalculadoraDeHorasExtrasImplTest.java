package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.COMUM;
import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.FERIADO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraDeHorasExtrasImplTest {

    private final int MES = 6;
    private final int ANO = 2025;
    private final int SEMANA = 2;
    private final LocalTime ENTRADA = LocalTime.of(9, 0);
    private final LocalTime INICIO_ALMOCO = LocalTime.of(11, 0);
    private final LocalTime FIM_ALMOCO = LocalTime.of(12, 0);
    private final LocalTime SAIDA = LocalTime.of(18, 0);
    private Atendente atendente;
    private Loja loja;
    private CalculadoraDeHorasExtrasImpl calculadoraDeHorasExtras;
    private PontoEletronico pontoEletronico1;
    private PontoEletronico pontoEletronico2;
    private PontoEletronico pontoEletronico3;
    private PontoEletronico pontoEletronico4;
    private PontoEletronico pontoEletronico5;
    private PontoEletronico pontoEletronico6;

    @BeforeEach
    void setUp() {
        loja = new Loja("Google", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(2000));
        calculadoraDeHorasExtras = new CalculadoraDeHorasExtrasImpl();

        pontoEletronico1 = new PontoEletronico(LocalDate.of(ANO, MES, 7),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);
        pontoEletronico2 = new PontoEletronico(LocalDate.of(ANO, MES, 8),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);

        pontoEletronico3 = new PontoEletronico(LocalDate.of(ANO, MES, 9),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);

        pontoEletronico4 = new PontoEletronico(LocalDate.of(ANO, MES, 10),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);

        pontoEletronico5 = new PontoEletronico(LocalDate.of(ANO, MES, 11),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);

        pontoEletronico6 = new PontoEletronico(LocalDate.of(ANO, MES, 12),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);
    }

    @Test
    void deveCalcularHorasExtrasSemanais_comSucesso() {
        var pontoEletronicoList = List.of(pontoEletronico1, pontoEletronico2, pontoEletronico3,
                pontoEletronico4, pontoEletronico5, pontoEletronico6);
        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontoEletronicoList);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(Duration.ofHours(4), resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasSemanais_comSucesso_mesmoHavendoHorasDiarias() {
        var pontoComHorasExtrasDiarias = new PontoEletronico(LocalDate.of(ANO, MES, 7),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusHours(1),
                COMUM,
                atendente);
        var pontoEletronicoList = List.of(pontoComHorasExtrasDiarias, pontoEletronico2, pontoEletronico3,
                pontoEletronico4, pontoEletronico5, pontoEletronico6);
        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontoEletronicoList);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(Duration.ofHours(5), resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasSemanais_mesmoHavendoHorasDiarias() {
        var pontoComHorasDiarias1 = new PontoEletronico(LocalDate.of(ANO, MES, 7),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);
        var pontoComHorasDiarias2 = new PontoEletronico(LocalDate.of(ANO, MES, 8),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias3 = new PontoEletronico(LocalDate.of(ANO, MES, 9),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias4 = new PontoEletronico(LocalDate.of(ANO, MES, 10),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias5 = new PontoEletronico(LocalDate.of(ANO, MES, 11),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias6 = new PontoEletronico(LocalDate.of(ANO, MES, 12),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontosComHorasDiariasList = List.of(pontoComHorasDiarias1, pontoComHorasDiarias2, pontoComHorasDiarias3,
                pontoComHorasDiarias4, pontoComHorasDiarias5, pontoComHorasDiarias6);

        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontosComHorasDiariasList);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(Duration.ofHours(7), resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasDiarias_quandoHorasSemanaisForemMenores() {
        var pontoComHorasDiarias1 = new PontoEletronico(LocalDate.of(ANO, MES, 7),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);
        var pontoComHorasDiarias2 = new PontoEletronico(LocalDate.of(ANO, MES, 8),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias3 = new PontoEletronico(LocalDate.of(ANO, MES, 9),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias4 = new PontoEletronico(LocalDate.of(ANO, MES, 10),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias5 = new PontoEletronico(LocalDate.of(ANO, MES, 11),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);

        var pontoComHorasDiarias6 = new PontoEletronico(LocalDate.of(ANO, MES, 12),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.minusHours(5),
                COMUM,
                atendente);

        var pontosComHorasDiariasList = List.of(pontoComHorasDiarias1, pontoComHorasDiarias2, pontoComHorasDiarias3,
                pontoComHorasDiarias4, pontoComHorasDiarias5, pontoComHorasDiarias6);

        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontosComHorasDiariasList);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(Duration.ofHours(1), resultado.get(atendente));
    }

    @Test
    void deveCalcularValorAReceber_comSucesso() {
        var salario = BigDecimal.valueOf(2000);
        var horasExtras = Duration.ofHours(2);
        var valorAReceber = BigDecimal.valueOf(25).setScale(2, RoundingMode.HALF_UP);

        var resultado = calculadoraDeHorasExtras.calcularValorAReceber(salario, horasExtras, BigDecimal.valueOf(0.5));

        assertEquals(valorAReceber, resultado);
    }

    @Test
    void deveCalculadorValorAReceberComAdicionalDeCemPorCento() {
        var salario = BigDecimal.valueOf(2000);
        var horasExtras = Duration.ofHours(1);
        var valorAReceber = BigDecimal.valueOf(16.67).setScale(2, RoundingMode.HALF_UP);

        var resultado = calculadoraDeHorasExtras.calcularValorAReceber(salario, horasExtras, BigDecimal.valueOf(1));

        assertEquals(valorAReceber, resultado);
    }

    @Test
    void deveCalcularHorasExtrasDiarias_quandoForDomingo() {
        var domingo = 1;
        var pontoDeDomingo = new PontoEletronico(LocalDate.of(ANO, MES, domingo),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                LocalTime.of(16, 30),
                COMUM,
                atendente);

        var pontosDeDomingoList = List.of(pontoDeDomingo);
        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontosDeDomingoList);

        var resutlado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(Duration.ofMinutes(30), resutlado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasDiarias_quandoFoFeriado() {
        var pontoFeriado = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                LocalTime.of(16, 30),
                FERIADO,
                atendente);

        var pontosDeFeriadoList = List.of(pontoFeriado);
        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontosDeFeriadoList);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(Duration.ofMinutes(30), resultado.get(atendente));
    }
}