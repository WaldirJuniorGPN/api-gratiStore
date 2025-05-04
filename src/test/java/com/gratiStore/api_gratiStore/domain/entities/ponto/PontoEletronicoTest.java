package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class PontoEletronicoTest {

    private Atendente atendente;

    @BeforeEach
    void setUp() {
        var loja = new Loja("Americanas", "06026378000140");
        this.atendente = new Atendente("Fulano", loja);
    }

    @Test
    void deveCriarPontoEletronicoComSucesso() {
        var ponto = new PontoEletronico(LocalDate.now(),
                LocalTime.of(7,0),
                LocalTime.of(11,0),
                LocalTime.of(12,0),
                LocalTime.of(18,0),
                this.atendente);

        assertEquals(LocalTime.of(7,0), ponto.getEntrada());
        assertEquals(LocalTime.of(11,0), ponto.getInicioAlmoco());
        assertEquals("Fulano", ponto.getAtendente().getNome());
    }

    @Test
    void deveFalharAoAtribuirDataNoFuturo() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now().plusDays(1),
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirDataNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(null,
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraEntradaNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        null,
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraInicioAlmocoInvalido() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        LocalTime.of(6,0),
                        LocalTime.of(12,0),
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraInicioAlmocoNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        null,
                        LocalTime.of(12,0),
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraFimAlmocoInvalido() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        LocalTime.of(10,0),
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraFimAlmocoNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        null,
                        LocalTime.of(18,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraSaidaInvalida() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        LocalTime.of(10,0),
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirHoraSaidaNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        null,
                        this.atendente));
    }

    @Test
    void deveFalharAoAtribuirAtendenteNull() {
        assertThrows(ValidacaoNegocioException.class,
                () -> new PontoEletronico(LocalDate.now(),
                        LocalTime.of(7,0),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        LocalTime.of(18,0),
                        null));
    }
}