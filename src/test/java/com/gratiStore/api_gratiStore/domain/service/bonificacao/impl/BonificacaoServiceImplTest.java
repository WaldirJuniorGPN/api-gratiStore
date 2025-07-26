package com.gratiStore.api_gratiStore.domain.service.bonificacao.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.SIM;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BonificacaoServiceImplTest {

    private Atendente atendente1;
    private Atendente atendente2;
    private Atendente atendente3;
    private Atendente atendente4;
    private Loja loja;
    private List<Atendente> atendenteList;
    private BonificacaoServiceImpl bonificacaoService;
    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        loja = new Loja("Google", "06026378000140");
        calculadora = new Calculadora("Calculadora",
                10,
                5,
                3,
                1,
                BigDecimal.valueOf(40),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(10),
                loja);
        bonificacaoService = new BonificacaoServiceImpl();
        atendente1 = new Atendente("Fulano", loja, BigDecimal.valueOf(15000));
        atendente2 = new Atendente("Beltrano", loja, BigDecimal.valueOf(15000));
        atendente3 = new Atendente("Ciclano", loja, BigDecimal.valueOf(15000));
        atendente4 = new Atendente("Tibetano", loja, BigDecimal.valueOf(15000));
        atendenteList = List.of(atendente1, atendente2, atendente3, atendente4);
    }

    @Test
    void deveCalcularBonificacaoPrimeiraSemana_quandoAtrasoFalse() {
        atendente1.setQuantidadeAtendimentosPrimeiraSemana(100);
        atendente2.setQuantidadeAtendimentosPrimeiraSemana(90);
        atendente3.setQuantidadeAtendimentosPrimeiraSemana(80);
        atendente4.setQuantidadeAtendimentosPrimeiraSemana(70);
        atendente1.setBonus(BigDecimal.valueOf(500));
        atendente2.setBonus(BigDecimal.valueOf(500));
        atendente3.setBonus(BigDecimal.valueOf(500));
        atendente4.setBonus(BigDecimal.valueOf(500));

        bonificacaoService.calcularBonusPrimeiraSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente4.getBonus()));
    }

    @Test
    void deveZerarBonificacaoPrimeiraSemana_quandoAtrasoTrue() {
        atendente1.setQuantidadeAtendimentosPrimeiraSemana(100);
        atendente2.setQuantidadeAtendimentosPrimeiraSemana(90);
        atendente3.setQuantidadeAtendimentosPrimeiraSemana(80);
        atendente1.setBonus(BigDecimal.valueOf(500));
        atendente2.setBonus(BigDecimal.valueOf(500));
        atendente3.setBonus(BigDecimal.valueOf(500));
        atendente1.setAtrasoStatusPrimeiraSemana(SIM);
        atendente2.setAtrasoStatusPrimeiraSemana(SIM);
        atendente3.setAtrasoStatusPrimeiraSemana(SIM);

        bonificacaoService.calcularBonusPrimeiraSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()));
    }

    @Test
    void deveCalcularBonificacaoSegundaSemana_quandoAtrasoFalse() {
        atendente1.setQuantidadeAtendimentosSegundaSemana(100);
        atendente2.setQuantidadeAtendimentosSegundaSemana(90);
        atendente3.setQuantidadeAtendimentosSegundaSemana(80);
        atendente4.setQuantidadeAtendimentosSegundaSemana(70);

        bonificacaoService.calcularBonusSegundaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente4.getBonus()));
    }

    @Test
    void deveZerarBonificacaoSegundaSemana_quandoAtrasoTrue() {
        atendente1.setQuantidadeAtendimentosSegundaSemana(100);
        atendente2.setQuantidadeAtendimentosSegundaSemana(90);
        atendente3.setQuantidadeAtendimentosSegundaSemana(80);
        atendente1.setAtrasoStatusSegundaSemana(SIM);
        atendente2.setAtrasoStatusSegundaSemana(SIM);
        atendente3.setAtrasoStatusSegundaSemana(SIM);

        bonificacaoService.calcularBonusSegundaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.ZERO, atendente1.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente2.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente3.getBonus()));
    }

    @Test
    void deveCalcularBonificacaoTerceiraSemana_quandoAtrasoFalse() {
        atendente1.setQuantidadeAtendimentosTerceiraSemana(100);
        atendente2.setQuantidadeAtendimentosTerceiraSemana(90);
        atendente3.setQuantidadeAtendimentosTerceiraSemana(80);
        atendente4.setQuantidadeAtendimentosTerceiraSemana(70);

        bonificacaoService.calcularBonusTerceiraSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente4.getBonus()));
    }

    @Test
    void deveZerarBonificacaoTerceiraSemana_quandoAtrasoTrue() {
        atendente1.setQuantidadeAtendimentosTerceiraSemana(100);
        atendente2.setQuantidadeAtendimentosTerceiraSemana(90);
        atendente3.setQuantidadeAtendimentosTerceiraSemana(80);
        atendente1.setAtrasoStatusTerceiraSemana(SIM);
        atendente2.setAtrasoStatusTerceiraSemana(SIM);
        atendente3.setAtrasoStatusTerceiraSemana(SIM);

        bonificacaoService.calcularBonusTerceiraSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.ZERO, atendente1.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente2.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente3.getBonus()));
    }

    @Test
    void deveCalcularBonificacaoQuartaSemana_quandoAtrasoFalse() {
        atendente1.setQuantidadeAtendimentosQuartaSemana(100);
        atendente2.setQuantidadeAtendimentosQuartaSemana(90);
        atendente3.setQuantidadeAtendimentosQuartaSemana(80);
        atendente4.setQuantidadeAtendimentosQuartaSemana(70);

        bonificacaoService.calcularBonusQuartaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente4.getBonus()));
    }

    @Test
    void deveZerarBonificacaoQuartaSemana_quandoAtrasoTrue() {
        atendente1.setQuantidadeAtendimentosQuartaSemana(100);
        atendente2.setQuantidadeAtendimentosQuartaSemana(90);
        atendente3.setQuantidadeAtendimentosQuartaSemana(80);
        atendente1.setAtrasoStatusQuartaSemana(SIM);
        atendente2.setAtrasoStatusQuartaSemana(SIM);
        atendente3.setAtrasoStatusQuartaSemana(SIM);

        bonificacaoService.calcularBonusQuartaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.ZERO, atendente1.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente2.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente3.getBonus()));
    }

    @Test
    void deveCalcularBonificacaoQuintaSemana_quandoAtrasoFalse() {
        atendente1.setQuantidadeAtendimentosQuartaSemana(100);
        atendente2.setQuantidadeAtendimentosQuartaSemana(90);
        atendente3.setQuantidadeAtendimentosQuartaSemana(80);
        atendente4.setQuantidadeAtendimentosQuartaSemana(70);

        bonificacaoService.calcularBonusQuartaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente4.getBonus()));
    }

    @Test
    void deveZerarBonificacaoQuintaSemana_quandoAtrasoTrue() {
        atendente1.setQuantidadeAtendimentosQuintaSemana(100);
        atendente2.setQuantidadeAtendimentosQuintaSemana(90);
        atendente3.setQuantidadeAtendimentosQuintaSemana(80);
        atendente1.setAtrasoStatusQuintaSemana(SIM);
        atendente2.setAtrasoStatusQuintaSemana(SIM);
        atendente3.setAtrasoStatusQuintaSemana(SIM);

        bonificacaoService.calcularBonusQuintaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.ZERO, atendente1.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente2.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente3.getBonus()));
    }

    @Test
    void deveCalcularBonificacaoSextaSemana_quandoAtrasoFalse() {
        atendente1.setQuantidadeAtendimentosSextaSemana(100);
        atendente2.setQuantidadeAtendimentosSextaSemana(90);
        atendente3.setQuantidadeAtendimentosSextaSemana(80);
        atendente4.setQuantidadeAtendimentosSextaSemana(70);

        bonificacaoService.calcularBonusSextaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP), atendente1.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), atendente2.getBonus()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente3.getBonus()),
                () -> assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), atendente4.getBonus()));
    }

    @Test
    void deveZerarBonificacaoSextaSemana_quandoAtrasoTrue() {
        atendente1.setQuantidadeAtendimentosSextaSemana(100);
        atendente2.setQuantidadeAtendimentosSextaSemana(90);
        atendente3.setQuantidadeAtendimentosSextaSemana(80);
        atendente1.setAtrasoStatusSextaSemana(SIM);
        atendente2.setAtrasoStatusSextaSemana(SIM);
        atendente3.setAtrasoStatusSextaSemana(SIM);

        bonificacaoService.calcularBonusSextaSemana(atendenteList, calculadora);

        assertAll("Verifica o cálculo de bônus",
                () -> assertEquals(BigDecimal.ZERO, atendente1.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente2.getBonus()),
                () -> assertEquals(BigDecimal.ZERO, atendente3.getBonus()));
    }
}