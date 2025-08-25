package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.vo.ResultadoPreliminar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.*;
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
    private CalculadoraDeHorasExtrasImpl calculadoraDeHorasExtras;
    private PontoEletronico pontoEletronico1;
    private PontoEletronico pontoEletronico2;
    private PontoEletronico pontoEletronico3;
    private PontoEletronico pontoEletronico4;
    private PontoEletronico pontoEletronico5;
    private PontoEletronico pontoEletronico6;

    @BeforeEach
    void setUp() {
        var loja = new Loja("Google", "06026378000140");
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
        var h50 = Duration.ofHours(4);
        var h100 = Duration.ZERO;
        var valor50 = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP);
        var valor100 = BigDecimal.ZERO;
        var resultadoPreliminar = new ResultadoPreliminar(h50, h100, valor50, valor100);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(resultadoPreliminar, resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasSemanais_comSucesso_mesmoHavendoHorasDiarias() {
        var h50 = Duration.ofHours(5);
        var h100 = Duration.ZERO;
        var valor50 = BigDecimal.valueOf(62.5).setScale(2, RoundingMode.HALF_UP);
        var valor100 = BigDecimal.ZERO;
        var resultadoPreliminar = new ResultadoPreliminar(h50, h100, valor50, valor100);
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

        assertEquals(resultadoPreliminar, resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasSemanais_mesmoHavendoHorasDiarias() {
        var pontoComHorasDiarias1 = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);
        var pontoComHorasDiarias2 = new PontoEletronico(LocalDate.of(ANO, MES, 3),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias3 = new PontoEletronico(LocalDate.of(ANO, MES, 4),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias4 = new PontoEletronico(LocalDate.of(ANO, MES, 5),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias5 = new PontoEletronico(LocalDate.of(ANO, MES, 6),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias6 = new PontoEletronico(LocalDate.of(ANO, MES, 7),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontosComHorasDiariasList = List.of(pontoComHorasDiarias1, pontoComHorasDiarias2, pontoComHorasDiarias3,
                pontoComHorasDiarias4, pontoComHorasDiarias5, pontoComHorasDiarias6);

        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontosComHorasDiariasList);
        var h50 = Duration.ofHours(7);
        var h100 = Duration.ZERO;
        var valor50 = BigDecimal.valueOf(87.50).setScale(2, RoundingMode.HALF_UP);
        var valor100 = BigDecimal.ZERO;
        var resultadoPreliminar = new ResultadoPreliminar(h50, h100, valor50, valor100);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(resultadoPreliminar, resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasDiarias_quandoHorasSemanaisForemMenores() {
        var pontoComHorasDiarias1 = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);
        var pontoComHorasDiarias2 = new PontoEletronico(LocalDate.of(ANO, MES, 3),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias3 = new PontoEletronico(LocalDate.of(ANO, MES, 4),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias4 = new PontoEletronico(LocalDate.of(ANO, MES, 5),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusMinutes(30),
                COMUM,
                atendente);

        var pontoComHorasDiarias5 = new PontoEletronico(LocalDate.of(ANO, MES, 6),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente);

        var pontosComHorasDiariasList = List.of(pontoComHorasDiarias1, pontoComHorasDiarias2, pontoComHorasDiarias3,
                pontoComHorasDiarias4, pontoComHorasDiarias5);

        var pontosAgrupadosPorSemana = Map.of(SEMANA, pontosComHorasDiariasList);
        var h50 = Duration.ofHours(2);
        var h100 = Duration.ZERO;
        var valor50 = BigDecimal.valueOf(25).setScale(2, RoundingMode.HALF_UP);
        var valor100 = BigDecimal.ZERO;
        var resultadoPreliminar = new ResultadoPreliminar(h50, h100, valor50, valor100);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(resultadoPreliminar, resultado.get(atendente));
    }

    @Test
    void deveCalcularHorasExtrasDiarias_comAdicionalDe100PorCento() {
        var expectativa = new ResultadoPreliminar(Duration.ofHours(2),
                Duration.ofHours(1),
                BigDecimal.valueOf(25).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(16.666).setScale(2, RoundingMode.HALF_UP));
        var mapExpectativa = Map.of(atendente, expectativa);

        var ponto1 = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusHours(3),
                COMUM,
                atendente);

        var pontosList = List.of(ponto1);
        var mapPonto = Map.of(SEMANA, pontosList);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(mapPonto);

        assertEquals(mapExpectativa, resultado);
    }

    @Test
    void deveDescontarEfetuarDescontoDasHoras_quandoStatusForCompativel() {

        var ponto1 = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                null,
                null,
                null,
                null,
                DESCONTAR_EM_HORAS,
                atendente);
        var ponto2 = new PontoEletronico(LocalDate.of(ANO, MES, 3),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusHours(5),
                COMUM,
                atendente);
        var ponto3 = new PontoEletronico(LocalDate.of(ANO, MES, 4),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusHours(4),
                COMUM,
                atendente);

        var mapPonto = Map.of(SEMANA, List.of(ponto1, ponto2, ponto3));

        var expectativa = new ResultadoPreliminar(Duration.ofHours(1),
                Duration.ZERO,
                BigDecimal.valueOf(12.5).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.ZERO);
        var mapExpectativa = Map.of(atendente, expectativa);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(mapPonto);

        assertEquals(mapExpectativa, resultado);
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
        var h50 = Duration.ofMinutes(30);
        var h100 = Duration.ZERO;
        var valor50 = BigDecimal.valueOf(6.25).setScale(2, RoundingMode.HALF_UP);
        var valor100 = BigDecimal.ZERO;
        var resultadoPreliminar = new ResultadoPreliminar(h50, h100, valor50, valor100);

        var resutlado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(resultadoPreliminar, resutlado.get(atendente));
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
        var h50 = Duration.ofMinutes(30);
        var h100 = Duration.ZERO;
        var valor50 = BigDecimal.valueOf(6.25).setScale(2, RoundingMode.HALF_UP);
        var valor100 = BigDecimal.ZERO;
        var resultadoPreliminar = new ResultadoPreliminar(h50, h100, valor50, valor100);

        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana);

        assertEquals(resultadoPreliminar, resultado.get(atendente));
    }

    @Test
    void deveGerarHorasZeradas_quandoAtestadoMatutinoAntesDoMeioDia() {
        var ponto = new PontoEletronico(LocalDate.of(ANO, MES, 13),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(17, 0),
                ATESTADO_MATUTINO,
                atendente);

        var pontosAgrupados = Map.of(SEMANA, List.of(ponto));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupados);

        assertEquals(Duration.ZERO, resultado.get(atendente).horas50());
        assertEquals(Duration.ZERO, resultado.get(atendente).horas100());
    }

    @Test
    void deveGerarHorasZeradas_quandoAtestadoMatutinoEntradaMeioDia() {
        var ponto = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                LocalTime.NOON,
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                SAIDA,
                ATESTADO_MATUTINO,
                atendente);

        var pontosAgrupados = Map.of(SEMANA, List.of(ponto));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupados);

        assertEquals(Duration.ZERO, resultado.get(atendente).horas50());
        assertEquals(Duration.ZERO, resultado.get(atendente).horas100());
    }

    @Test
    void deveGerarHorasNegativas_quandoAtestadoMatutinoComEntradaAposMeioDia() {
        var ponto = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                SAIDA,
                ATESTADO_MATUTINO,
                atendente);

        var pontosAgrupados = Map.of(SEMANA, List.of(ponto));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupados);

        assertEquals(Duration.ofMinutes(-30), resultado.get(atendente).horas50());
        assertEquals(Duration.ZERO, resultado.get(atendente).horas100());
    }

    @Test
    void deveGerarHorasNegativas_quandoAtestadoVespertinoESaidaForAntesDeMeioDia() {
        var ponto = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                LocalTime.of(11, 30),
                LocalTime.of(11, 40),
                ATESTADO_VESPERTINO,
                atendente);

        var pontosAgrupados = Map.of(SEMANA, List.of(ponto));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupados);

        assertEquals(Duration.ofMinutes(-20), resultado.get(atendente).horas50());
        assertEquals(Duration.ZERO, resultado.get(atendente).horas100());
    }

    @Test
    void deveGerarHorasZeradas_quandoAtestadoVespertinoESaidaForIgualAMeioDia() {
        var ponto = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                LocalTime.NOON,
                ATESTADO_VESPERTINO,
                atendente);

        var pontoAgrupados = Map.of(SEMANA, List.of(ponto));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontoAgrupados);

        assertEquals(Duration.ZERO, resultado.get(atendente).horas50());
    }

    @Test
    void deveGerarHorasZeradas_quandoAtestadoVespertinoESaidaForDepoisDeMeioDia() {
        var ponto = new PontoEletronico(LocalDate.of(ANO, MES, 2),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                LocalTime.of(12, 30),
                ATESTADO_VESPERTINO,
                atendente);

        var pontoAgrupados = Map.of(SEMANA, List.of(ponto));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontoAgrupados);

        assertEquals(Duration.ZERO, resultado.get(atendente).horas50());
    }

    @Test
    void naoDeveConsiderarPontosComStatusQueNaoEntramNoCalculo() {
        var pontoComum = new PontoEletronico(LocalDate.of(ANO, MES, 16),
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusHours(1),
                COMUM,
                atendente);

        var pontoFolga = new PontoEletronico(LocalDate.of(ANO, MES, 17),
                null,
                null,
                null,
                null,
                FOLGA,
                atendente);

        var pontosAgrupados = Map.of(SEMANA, List.of(pontoComum, pontoFolga));
        var resultado = calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupados);

        assertEquals(Duration.ofHours(1), resultado.get(atendente).horas50());
        assertEquals(Duration.ZERO, resultado.get(atendente).horas100());
    }
}