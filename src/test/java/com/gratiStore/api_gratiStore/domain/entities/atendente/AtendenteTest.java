package com.gratiStore.api_gratiStore.domain.entities.atendente;

import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.SIM;
import static org.junit.jupiter.api.Assertions.*;

class AtendenteTest {

    private Loja loja;
    private Atendente atendente;

    @BeforeEach
    void setUp() {
        this.loja = new Loja("Americanas", "06026378000140");
        this.atendente = new Atendente("Pedro", loja, BigDecimal.ZERO);
    }

    @Test
    void deveCriarAtendenteComSucesso() {
        var atendente = new Atendente("João", loja, new BigDecimal("2000"));
        assertEquals("João", atendente.getNome());
        assertEquals(loja, atendente.getLoja());
        assertEquals(new BigDecimal("2000.00"), atendente.getSalario());
    }

    @Test
    void deveFalharAoCriarAtendenteComSalarioInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> atendente.setSalario(new BigDecimal("-1")));
    }

    @Test
    void deveFalharAoCriarAtendenteComSalarioNull() {
        assertThrows(ValidacaoNegocioException.class, () -> atendente.setSalario(null));
    }

    @Test
    void deveFalharAoCriarAtendenteComNomeInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> new Atendente(" ", loja, BigDecimal.ZERO));
    }

    @Test
    void deveFalharAoCriarAtendenteComNomeNull() {
        assertThrows(ValidacaoNegocioException.class, () -> new Atendente(null, loja, BigDecimal.ZERO));
    }

    @Test
    void deveFalharAoCriarAtendenteComLojaNull() {
        assertThrows(ValidacaoNegocioException.class, () -> new Atendente("João", null, BigDecimal.ZERO));
    }

    @Test
    void deveAtribuirVendasPrimeiraSemanaComSucesso() {
        this.atendente.setVendasPrimeiraSemana(new BigDecimal("200.90"));
        assertEquals(new BigDecimal("200.90"), this.atendente.getVendasPrimeiraSemana());
    }

    @Test
    void deveFalharAoAtribuirVendasPrimeiraSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setVendasPrimeiraSemana(new BigDecimal("-100")));
    }

    @Test
    void deveFalharAoAtribuirVendasPrimeiraSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setVendasPrimeiraSemana(null));
    }

    @Test
    void deveAtribuirVendasSegundaSemanaComSucesso() {
        this.atendente.setVendasSegundaSemana(new BigDecimal("300.50"));
        assertEquals(new BigDecimal("300.50"), this.atendente.getVendasSegundaSemana());
    }

    @Test
    void deveFalharAoAtribuirVendasSegundaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setVendasSegundaSemana(new BigDecimal("-200")));
    }

    @Test
    void deveFalharAoAtribuirVendasSegundaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setVendasSegundaSemana(null));
    }

    @Test
    void deveAtribuirVendasTerceiraSemanaComSucesso() {
        this.atendente.setVendasTerceiraSemana(new BigDecimal("400.75"));
        assertEquals(new BigDecimal("400.75"), this.atendente.getVendasTerceiraSemana());
    }

    @Test
    void deveFalharAoAtribuirVendasTerceiraSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setVendasTerceiraSemana(new BigDecimal("-300")));
    }

    @Test
    void deveFalharAoAtribuirVendasTerceiraSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setVendasTerceiraSemana(null));
    }

    @Test
    void deveAtribuirVendasQuartaSemanaComSucesso() {
        this.atendente.setVendasQuartaSemana(new BigDecimal("500.25"));
        assertEquals(new BigDecimal("500.25"), this.atendente.getVendasQuartaSemana());
    }

    @Test
    void deveFalharAoAtribuirVendasQuartaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setVendasQuartaSemana(new BigDecimal("-400")));
    }

    @Test
    void deveFalharAoAtribuirVendasQuartaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setVendasQuartaSemana(null));
    }

    @Test
    void deveAtribuirVendasQuintaSemanaComSucesso() {
        this.atendente.setVendasQuintaSemana(new BigDecimal("600.00"));
        assertEquals(new BigDecimal("600.00"), this.atendente.getVendasQuintaSemana());
    }

    @Test
    void deveFalharAoAtribuirVendasQuintaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setVendasQuintaSemana(new BigDecimal("-500")));
    }

    @Test
    void deveFalharAoAtribuirVendasQuintaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setVendasQuintaSemana(null));
    }

    @Test
    void deveAtribuirVendasSextaSemanaComSucesso() {
        this.atendente.setVendasSextaSemana(new BigDecimal("700.80"));
        assertEquals(new BigDecimal("700.80"), this.atendente.getVendasSextaSemana());
    }

    @Test
    void deveFalharAoAtribuirVendasSextaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setVendasSextaSemana(new BigDecimal("-600")));
    }

    @Test
    void deveFalharAoAtribuirVendasSextaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setVendasSextaSemana(null));
    }

    @Test
    void deveAtribuirQuantidadeAtendimentosPrimeiraSemanaComSucesso() {
        this.atendente.setQuantidadeAtendimentosPrimeiraSemana(100);
        assertEquals(100, this.atendente.getQuantidadeAtendimentosPrimeiraSemana());
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosPrimeiraSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setQuantidadeAtendimentosPrimeiraSemana(-10));
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosPrimeiraSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setQuantidadeAtendimentosPrimeiraSemana(null));
    }

    @Test
    void deveAtribuirQuantidadeAtendimentosSegundaSemanaComSucesso() {
        this.atendente.setQuantidadeAtendimentosSegundaSemana(120);
        assertEquals(120, this.atendente.getQuantidadeAtendimentosSegundaSemana());
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosSegundaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosSegundaSemana(-15));
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosSegundaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setQuantidadeAtendimentosSegundaSemana(null));
    }

    @Test
    void deveAtribuirQuantidadeAtendimentosTerceiraSemanaComSucesso() {
        this.atendente.setQuantidadeAtendimentosTerceiraSemana(130);
        assertEquals(130, this.atendente.getQuantidadeAtendimentosTerceiraSemana());
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosTerceiraSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setQuantidadeAtendimentosTerceiraSemana(-20));
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosTerceiraSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> this.atendente.setQuantidadeAtendimentosTerceiraSemana(null));
    }

    @Test
    void deveAtribuirQuantidadeAtendimentosQuartaSemanaComSucesso() {
        this.atendente.setQuantidadeAtendimentosQuartaSemana(140);
        assertEquals(140, this.atendente.getQuantidadeAtendimentosQuartaSemana());
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosQuartaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosQuartaSemana(-25));
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosQuartaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosQuartaSemana(null));
    }

    @Test
    void deveAtribuirQuantidadeAtendimentosQuintaSemanaComSucesso() {
        this.atendente.setQuantidadeAtendimentosQuintaSemana(150);
        assertEquals(150, this.atendente.getQuantidadeAtendimentosQuintaSemana());
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosQuintaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosQuintaSemana(-30));
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosQuintaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosQuintaSemana(null));
    }

    @Test
    void deveAtribuirQuantidadeAtendimentosSextaSemanaComSucesso() {
        this.atendente.setQuantidadeAtendimentosSextaSemana(160);
        assertEquals(160, this.atendente.getQuantidadeAtendimentosSextaSemana());
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosSextaSemanaComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosSextaSemana(-35));
    }

    @Test
    void deveFalharAoAtribuirQuantidadeAtendimentosSextaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setQuantidadeAtendimentosSextaSemana(null));
    }

    @Test
    void deveAtribuirAtrasoStatusPrimeiraSemanaComSucesso() {
        this.atendente.setAtrasoStatusPrimeiraSemana(SIM);
        assertEquals(SIM, this.atendente.getAtrasoStatusPrimeiraSemana());
    }

    @Test
    void deveFalharAoAtribuirAtrasoStatusPrimeiraSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setAtrasoStatusPrimeiraSemana(null));
    }

    @Test
    void deveAtribuirAtrasoStatusSegundaSemanaComSucesso() {
        this.atendente.setAtrasoStatusSegundaSemana(SIM);
        assertEquals(SIM, this.atendente.getAtrasoStatusSegundaSemana());
    }

    @Test
    void deveFalharAoAtribuirAtrasoStatusSegundaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setAtrasoStatusSegundaSemana(null));
    }

    @Test
    void deveAtribuirAtrasoStatusTerceiraSemanaComSucesso() {
        this.atendente.setAtrasoStatusTerceiraSemana(SIM);
        assertEquals(SIM, this.atendente.getAtrasoStatusTerceiraSemana());
    }

    @Test
    void deveFalharAoAtribuirAtrasoStatusTerceiraSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setAtrasoStatusTerceiraSemana(null));
    }

    @Test
    void deveAtribuirAtrasoStatusQuartaSemanaComSucesso() {
        this.atendente.setAtrasoStatusQuartaSemana(SIM);
        assertEquals(SIM, this.atendente.getAtrasoStatusQuartaSemana());
    }

    @Test
    void deveFalharAoAtribuirAtrasoStatusQuartaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setAtrasoStatusQuartaSemana(null));
    }

    @Test
    void deveAtribuirAtrasoStatusQuintaSemanaComSucesso() {
        this.atendente.setAtrasoStatusQuintaSemana(SIM);
        assertEquals(SIM, this.atendente.getAtrasoStatusQuintaSemana());
    }

    @Test
    void deveFalharAoAtribuirAtrasoStatusQuintaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setAtrasoStatusQuintaSemana(null));
    }

    @Test
    void deveAtribuirAtrasoStatusSextaSemanaComSucesso() {
        this.atendente.setAtrasoStatusSextaSemana(SIM);
        assertEquals(SIM, this.atendente.getAtrasoStatusSextaSemana());
    }

    @Test
    void deveFalharAoAtribuirAtrasoStatusSextaSemanaComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setAtrasoStatusSextaSemana(null));
    }

    @Test
    void deveAtribuirBonusComSucesso() {
        this.atendente.setBonus(new BigDecimal("100"));
        assertEquals(new BigDecimal("100.00"), this.atendente.getBonus());
    }

    @Test
    void deveFalharAoAtribuirBonusComValorNegativo() {
        assertThrows(ValidacaoNegocioException.class, () -> atendente.setBonus(new BigDecimal("-200")));
    }

    @Test
    void deveFalharAoAtribuirBonusComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setBonus(null));
    }

    @Test
    void deveAtribuirGratificacaoComSucesso() {
        this.atendente.setGratificacao(new BigDecimal("100"));
        assertEquals(new BigDecimal("100.00"), this.atendente.getGratificacao());
    }

    @Test
    void deveFalharAoAtribuirGratificacaoComValorInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setGratificacao(new BigDecimal("-100")));
    }

    @Test
    void deveFalharAoAtribuirGratificacaoComvalorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setGratificacao(null));
    }

    @Test
    void deveAtribuirTotalVendasComSucesso() {
        this.atendente.setTotalVendas(new BigDecimal("200"));
        assertEquals(new BigDecimal("200.00"), this.atendente.getTotalVendas());
    }

    @Test
    void deveFalharAoAtribuirTotalVendasComValorInválido() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setTotalVendas(new BigDecimal("-100")));
    }

    @Test
    void deveFalharAoAtribuirTotalVendasComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.setTotalVendas(null));
    }

    @Test
    void deveIncrementarGratificacaoComSucesso() {
        this.atendente.atribuirGratificacao(new BigDecimal("100"));
        this.atendente.atribuirGratificacao(new BigDecimal("200"));
        assertEquals(new BigDecimal("300.00"), this.atendente.getGratificacao());
    }

    @Test
    void deveFalharAoIncrementarGratificacaoComValorInválido() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.atribuirGratificacao(new BigDecimal("-100")));
    }

    @Test
    void deveFalharAoIncrementarGratificacaoComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.atribuirGratificacao(null));
    }

    @Test
    void deveIncrementarBonusComSucesso() {
        this.atendente.atribuirBonus(new BigDecimal("50"));
        this.atendente.atribuirBonus(new BigDecimal("100"));
        assertEquals(new BigDecimal("150.00"), this.atendente.getBonus());
    }

    @Test
    void deveFalharAoIncrementarBonusComValorInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.atribuirBonus(new BigDecimal("-200")));
    }

    @Test
    void deveFalharAoIncrementarBonusComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.atribuirBonus(null));
    }

    @Test
    void deveAtribuirVendaTotalComSucesso() {
        this.atendente.atribuirVendaTotal(new BigDecimal("25"));
        this.atendente.atribuirVendaTotal(new BigDecimal("75"));
        assertEquals(new BigDecimal("100.00"), this.atendente.getTotalVendas());
    }

    @Test
    void deveFalharAoAtribuirVendaTotalComValorInvalido() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.atribuirVendaTotal(new BigDecimal("-250")));
    }

    @Test
    void deveFalharAoAtribuirVendaTotalComValorNull() {
        assertThrows(ValidacaoNegocioException.class, () -> this.atendente.atribuirVendaTotal(null));
    }
}