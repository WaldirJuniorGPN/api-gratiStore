package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.COMUM;
import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.FERIADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PontoEletronicoTest {

    private final LocalDate DATA = LocalDate.now();
    private final LocalTime ENTRADA = LocalTime.of(7, 0);
    private final LocalTime INICIO_ALMOCO = LocalTime.of(11, 0);
    private final LocalTime FIM_ALMOCO = LocalTime.of(12, 0);
    private final LocalTime SAIDA = LocalTime.of(18, 0);

    private Atendente atendente;
    private Loja loja;
    private PontoEletronico ponto;

    @BeforeEach
    void setUp() {
        this.loja = new Loja("Americanas", "06026378000140");
        this.atendente = new Atendente("Fulano", loja, BigDecimal.ZERO);
        this.ponto = new PontoEletronico(DATA,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                this.atendente);
    }

    @Test
    void deveCriarPontoEletronicoComSucesso() {

        assertEquals(ENTRADA, ponto.getEntrada());
        assertEquals(INICIO_ALMOCO, ponto.getInicioAlmoco());
        assertEquals(FIM_ALMOCO, ponto.getFimAlmoco());
        assertEquals(SAIDA, ponto.getSaida());
        assertEquals(COMUM, ponto.getStatus());
        assertEquals(atendente.getNome(), ponto.getAtendente().getNome());
    }

    @Test
    void deveFalharAoAtribuirDataNoFuturo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now().plusDays(1),
                        ENTRADA,
                        INICIO_ALMOCO,
                        FIM_ALMOCO,
                        SAIDA,
                        COMUM,
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirDataNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(null,
                        ENTRADA,
                        INICIO_ALMOCO,
                        FIM_ALMOCO,
                        SAIDA,
                        COMUM,
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraInicioAlmocoInvalido() {
        var horaInvalida = ENTRADA.minusHours(1);

        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        ENTRADA,
                        horaInvalida,
                        FIM_ALMOCO,
                        SAIDA,
                        COMUM,
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraFimAlmocoInvalido() {
        var horaInvalida = INICIO_ALMOCO.minusHours(1);

        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        ENTRADA,
                        INICIO_ALMOCO,
                        horaInvalida,
                        SAIDA,
                        COMUM,
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraSaidaInvalida() {
        var horaInvalida = FIM_ALMOCO.minusHours(1);

        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        ENTRADA,
                        INICIO_ALMOCO,
                        FIM_ALMOCO,
                        horaInvalida,
                        COMUM,
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirAtendenteNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        ENTRADA,
                        INICIO_ALMOCO,
                        FIM_ALMOCO,
                        SAIDA,
                        COMUM,
                        null));
    }

    @Test
    void deveAtualizarComSucesso() {
        var outroAtendente = new Atendente("Siclano", loja, BigDecimal.valueOf(5000));

        ponto.atualizarParametros(LocalDate.now().minusDays(1),
                ENTRADA.plusHours(1),
                INICIO_ALMOCO.plusHours(1),
                FIM_ALMOCO.plusHours(1),
                SAIDA.plusHours(1),
                FERIADO,
                outroAtendente);

        assertEquals(LocalDate.now().minusDays(1), ponto.getData());
        assertEquals(ENTRADA.plusHours(1), ponto.getEntrada());
        assertEquals(INICIO_ALMOCO.plusHours(1), ponto.getInicioAlmoco());
        assertEquals(FIM_ALMOCO.plusHours(1), ponto.getFimAlmoco());
        assertEquals(SAIDA.plusHours(1), ponto.getSaida());
        assertEquals(FERIADO, ponto.getStatus());
        assertEquals(outroAtendente, ponto.getAtendente());
    }

    @Test
    void deveFalharAoAtualizar_quandoDataEstiverNoFuturo() {
        var dataNoFuturo = LocalDate.now().plusDays(1);

        assertThrows(ValidacaoNegocioException.class, () -> ponto.atualizarParametros(dataNoFuturo,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente));
    }

    @Test
    void deveFalharAoAtualizar_quandoDataEstiverNull() {
        assertThrows(ValidacaoNegocioException.class, () -> ponto.atualizarParametros(null,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendente));
    }
}