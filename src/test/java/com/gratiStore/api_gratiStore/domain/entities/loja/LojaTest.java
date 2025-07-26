package com.gratiStore.api_gratiStore.domain.entities.loja;

import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LojaTest {

    private Loja loja;

    @BeforeEach
    void setUp() {
        this.loja = new Loja("Nome da Loja", "06026378000140");
    }

    @Test
    void deveCriarLojaComSucesso() {
        assertEquals("Nome da Loja", loja.getNome());
        assertEquals("06026378000140", loja.getCnpj().value());
    }

    @Test
    void deveFalharAoAtribuirNomeInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setNome(""));
    }

    @Test
    void deveFalharAoAtribuirNomeNull() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setNome(null));
    }

    @Test
    void deveFalharAoAtribuirCnpjComTamanhoInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setCnpj("060226378000140000"));
    }

    @Test
    void deveFalharAoAtribuirCnpjComValorInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setCnpj("00000999999999"));
    }

    @Test
    void deveFalharAoAtribuirCnpjNull() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setCnpj(null));
    }

    @Test
    void deveIncrementarVendasComSucesso() {
        loja.atribuirVendas(new BigDecimal("100"));
        loja.atribuirVendas(new BigDecimal("200"));
        assertEquals(new BigDecimal("300.00"), loja.getTotalVendas());
    }

    @Test
    void deveFalharAoIncrementarVendasComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.atribuirVendas(new BigDecimal("-1")));
    }

    @Test
    void deveFalharAoIncrementarVendasComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.atribuirVendas(null));
    }

    @Test
    void deveAtribuirTotalVendasComSucesso() {
        loja.setTotalVendas(new BigDecimal("2200"));
        assertEquals(new BigDecimal("2200"), loja.getTotalVendas());
    }

    @Test
    void deveFalharAoAtribuirTotalVendasComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setTotalVendas(new BigDecimal("-1")));
    }

    @Test
    void deveFalharAoAtribuirTotalVendasComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setTotalVendas(null));
    }

    @Test
    void deveAtribuirCalculadoraComSucesso() {
        var calculadora = new Calculadora("Calculadora 1",
                1.2,
                0.8,
                0.4,
                0.2,
                new BigDecimal("40"),
                new BigDecimal("20"),
                new BigDecimal("10"),
                this.loja);

        loja.setCalculadora(calculadora);
    }

    @Test
    void deveFalharAoAtribuirCalculadoraNull() {
        assertThrows(ValidacaoNegocioException.class, () -> loja.setCalculadora(null));
    }
 }