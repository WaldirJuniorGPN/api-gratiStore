package com.gratiStore.api_gratiStore.domain.entities.calculadora;

import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {

    private Loja loja;
    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        this.loja = new Loja("Americanas", "06026378000140");
        this.calculadora = new Calculadora("Calculadora 1",
                1.2,
                0.8,
                0.4,
                0.2,
                new BigDecimal("40"),
                new BigDecimal("20"),
                new BigDecimal("10"),
                this.loja);
    }

    @Test
    void deveCriarCalculadoraComSucesso() {

        assertEquals("Calculadora 1", calculadora.getNome());
        assertEquals(1.2 / 100, calculadora.getPercentualPrimeiroColocado());
        assertEquals(0.8 / 100, calculadora.getPercentualSegundoColocado());
        assertEquals(0.4 / 100, calculadora.getPercentualTerceiroColocado());
        assertEquals(0.2 / 100, calculadora.getPercentualDemaisColocados());
        assertEquals(new BigDecimal("40"), calculadora.getBonusPrimeiroColocado());
        assertEquals(new BigDecimal("20"), calculadora.getBonusSegundoColocado());
        assertEquals(new BigDecimal("10"), calculadora.getBonusTerceiroColocado());
        assertEquals(this.loja, calculadora.getLoja());
    }

    @Test
    void deveFalharAoAtribuirUmNomeInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setNome(" "));
    }

    @Test
    void deveFalharAoAtribuirNomeNull() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setNome(null));
    }

    @Test
    void deveFalharAoAtribuirPercentualPrimeiroColocadoNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setPercentualPrimeiroColocado(-1));
    }

    @Test
    void deveFalharAoAtribuirPercentualSegundoColocadoNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setPercentualSegundoColocado(-0.5));
    }

    @Test
    void deveFalharAoAtribuirPercentualTerceiroColocadoNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setPercentualTerceiroColocado(-0.3));
    }

    @Test
    void deveFalharAoAtribuirPercentualDemaisColocadosNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setPercentualDemaisColocados(-0.1));
    }

    @Test
    void deveFalharAoAtribuirBonusPrimeiraSemanaNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> calculadora.setBonusPrimeiroColocado(new BigDecimal("-10")));
    }

    @Test
    void deveFalharAoAtribuirBonusSegundoColocadoNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> calculadora.setBonusSegundoColocado(new BigDecimal("-20")));
    }

    @Test
    void deveFalharAoAtribuirBonusTerceiroColocadoNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> calculadora.setBonusTerceiroColocado(new BigDecimal("-10")));
    }

    @Test
    void deveFalharAoAtribuirBonusPrimeiroColocadoNull() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setBonusPrimeiroColocado(null));
    }

    @Test
    void deveFalharAoAtribuirBonusSegundoColocadoNull() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setBonusSegundoColocado(null));
    }

    @Test
    void deveFalharAoAtribuirBonusTerceiroColocadoNull() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setBonusTerceiroColocado(null));
    }

    @Test
    void deveFalharAoAtribuirLojaNull() {
        assertThrows(ValidacaoNegocioException.class, () -> calculadora.setLoja(null));
    }
}