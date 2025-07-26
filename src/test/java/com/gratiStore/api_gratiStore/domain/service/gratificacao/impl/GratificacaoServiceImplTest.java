package com.gratiStore.api_gratiStore.domain.service.gratificacao.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GratificacaoServiceImplTest {

    private Atendente atendente1;
    private Atendente atendente2;
    private Atendente atendente3;
    private Atendente atendente4;
    private List<Atendente> atendenteList;
    private Calculadora calculadora;
    private Loja loja;
    private GratificacaoServiceImpl servce = new GratificacaoServiceImpl();

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
        atendente1 = new Atendente("Fulano", loja, BigDecimal.valueOf(15000));
        atendente2 = new Atendente("Beltrano", loja, BigDecimal.valueOf(15000));
        atendente3 = new Atendente("Ciclano", loja, BigDecimal.valueOf(15000));
        atendente4 = new Atendente("Tibetano", loja, BigDecimal.valueOf(15000));
        atendenteList = List.of(atendente1, atendente2, atendente3, atendente4);
    }

    @Test
    void deveZerarECalcularGratificacaoPrimeiraSemana() {
        atendente1.setVendasPrimeiraSemana(BigDecimal.valueOf(10000));
        atendente2.setVendasPrimeiraSemana(BigDecimal.valueOf(5000));
        atendente3.setVendasPrimeiraSemana(BigDecimal.valueOf(3000));
        atendente4.setVendasPrimeiraSemana(BigDecimal.valueOf(1000));
        atendente1.setGratificacao(BigDecimal.valueOf(50000));
        atendente2.setGratificacao(BigDecimal.valueOf(50000));
        atendente3.setGratificacao(BigDecimal.valueOf(50000));
        atendente4.setGratificacao(BigDecimal.valueOf(50000));

        servce.calcularGratificacaoPrimeiraSemana(atendenteList, calculadora);

        assertAll("Verifica gratifação da Primeira Semana",
                () -> assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), atendente1.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(250).setScale(2, RoundingMode.HALF_UP), atendente2.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), atendente3.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente4.getGratificacao()));
    }

    @Test
    void deveCalcularGratificacaoSegundaSemana() {
        atendente1.setVendasSegundaSemana(BigDecimal.valueOf(10000));
        atendente2.setVendasSegundaSemana(BigDecimal.valueOf(5000));
        atendente3.setVendasSegundaSemana(BigDecimal.valueOf(3000));
        atendente4.setVendasSegundaSemana(BigDecimal.valueOf(1000));

        servce.calcularGratificacaoSegundaSemana(atendenteList, calculadora);

        assertAll("Verifica gratifação da Segunda Semana",
                () -> assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), atendente1.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(250).setScale(2, RoundingMode.HALF_UP), atendente2.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), atendente3.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente4.getGratificacao()));
    }

    @Test
    void deveCalcularGratificacaoTerceiraSemana() {
        atendente1.setVendasTerceiraSemana(BigDecimal.valueOf(10000));
        atendente2.setVendasTerceiraSemana(BigDecimal.valueOf(5000));
        atendente3.setVendasTerceiraSemana(BigDecimal.valueOf(3000));
        atendente4.setVendasTerceiraSemana(BigDecimal.valueOf(1000));

        servce.calcularGratificacaoTerceiraSemana(atendenteList, calculadora);

        assertAll("Verifica gratifação da Terceira Semana",
                () -> assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), atendente1.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(250).setScale(2, RoundingMode.HALF_UP), atendente2.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), atendente3.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente4.getGratificacao()));
    }

    @Test
    void deveCalcularGratificacaoQuartaSemana() {
        atendente1.setVendasQuartaSemana(BigDecimal.valueOf(10000));
        atendente2.setVendasQuartaSemana(BigDecimal.valueOf(5000));
        atendente3.setVendasQuartaSemana(BigDecimal.valueOf(3000));
        atendente4.setVendasQuartaSemana(BigDecimal.valueOf(1000));

        servce.calcularGratificacaoQuartaSemana(atendenteList, calculadora);

        assertAll("Verifica gratifação da Quarta Semana",
                () -> assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), atendente1.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(250).setScale(2, RoundingMode.HALF_UP), atendente2.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), atendente3.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente4.getGratificacao()));
    }

    @Test
    void deveCalcularGratificacaoQuintaSemana() {
        atendente1.setVendasQuintaSemana(BigDecimal.valueOf(10000));
        atendente2.setVendasQuintaSemana(BigDecimal.valueOf(5000));
        atendente3.setVendasQuintaSemana(BigDecimal.valueOf(3000));
        atendente4.setVendasQuintaSemana(BigDecimal.valueOf(1000));

        servce.calcularGratificacaoQuintaSemana(atendenteList, calculadora);

        assertAll("Verifica gratifação da Quinta Semana",
                () -> assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), atendente1.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(250).setScale(2, RoundingMode.HALF_UP), atendente2.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), atendente3.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente4.getGratificacao()));
    }

    @Test
    void deveCalcularGratificacaoSextaSemana() {
        atendente1.setVendasSextaSemana(BigDecimal.valueOf(10000));
        atendente2.setVendasSextaSemana(BigDecimal.valueOf(5000));
        atendente3.setVendasSextaSemana(BigDecimal.valueOf(3000));
        atendente4.setVendasSextaSemana(BigDecimal.valueOf(1000));

        servce.calcularGratificacaoSextaSemana(atendenteList, calculadora);

        assertAll("Verifica gratifação da Sexta Semana",
                () -> assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), atendente1.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(250).setScale(2, RoundingMode.HALF_UP), atendente2.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), atendente3.getGratificacao()),
                () -> assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), atendente4.getGratificacao()));
    }
}