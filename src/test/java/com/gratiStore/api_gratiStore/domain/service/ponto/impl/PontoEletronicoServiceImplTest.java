package com.gratiStore.api_gratiStore.domain.service.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;
import com.gratiStore.api_gratiStore.infra.adapter.ponto.PontoEletronicoAdapter;
import com.gratiStore.api_gratiStore.infra.repository.PontoEletronicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PontoEletronicoServiceImplTest {

    private final Long ATENDENTE_ID = 1L;

    @Mock
    private PontoEletronicoAdapter adapter;

    @Mock
    private PontoEletronicoRepository repository;

    @Mock
    private AtendenteService atendenteService;

    @Mock
    private LojaService lojaService;

    @InjectMocks
    private PontoEletronicoServiceImpl pontoEletronicoService;

    private Atendente atendente;
    private PontoEletronico ponto;
    private PontoRequest pontoRequest;
    private Loja loja;

    @BeforeEach
    void setUp() {
        loja = new Loja("Americanas", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(1500));
        pontoRequest = new PontoRequest(LocalDate.now().minusDays(1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17, 0),
                NAO,
                ATENDENTE_ID);
        ponto = new PontoEletronico(LocalDate.now().minusDays(1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17, 0),
                NAO,
                atendente);
    }

    @Test
    void deveRegistraPontoComSucesso() {
        when(atendenteService.buscarNoBanco(any(Long.class))).thenReturn(atendente);
        when(adapter.pontoRequestToPonto(any(PontoRequest.class), any(Atendente.class))).thenReturn(ponto);

        pontoEletronicoService.registrarPonto(pontoRequest);

        verify(atendenteService, times(1)).buscarNoBanco(any(Long.class));
        verify(adapter, times(1)).pontoRequestToPonto(any(PontoRequest.class), any(Atendente.class));
        verify(repository, times(1)).save(any(PontoEletronico.class));
    }
}