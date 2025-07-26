package com.gratiStore.api_gratiStore.domain.service.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.adapter.ponto.PontoEletronicoAdapter;
import com.gratiStore.api_gratiStore.infra.repository.PontoEletronicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils.NAO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PontoEletronicoServiceImplTest {

    private final Long ATENDENTE_ID = 1L;
    private final Long PONTO_ID = 1L;

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
    private HistoricoResponse historicoResponse;
    private List<Atendente> atendenteList;
    private Optional<PontoEletronico> pontoEletronicoOptional;

    @BeforeEach
    void setUp() {
        loja = new Loja("Americanas", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(1500));
        atendenteList = List.of(atendente);
        pontoRequest = new PontoRequest(LocalDate.of(2025, 1, 1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17, 0),
                NAO,
                ATENDENTE_ID);
        ponto = new PontoEletronico(LocalDate.of(2025, 1, 2),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17, 0),
                NAO,
                atendente);

        historicoResponse = new HistoricoResponse(PONTO_ID,
                LocalDate.of(2025, 1, 1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17, 0),
                NAO,
                ATENDENTE_ID);

        pontoEletronicoOptional = Optional.of(ponto);
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

    @Test
    void deveListarHistoricoDePontosComSucesso_retornarPaginado() {
        var pageable = PageRequest.of(0, 10);
        var pontos = List.of(ponto, ponto);
        var page = new PageImpl<>(pontos, pageable, pontos.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(adapter.pontoToHistoricoResponse(ponto)).thenReturn(historicoResponse);

        pontoEletronicoService.listarHistorico(pageable);

        verify(repository, times(1)).findAll(pageable);
        verify(adapter, times(2)).pontoToHistoricoResponse(any(PontoEletronico.class));
    }

    @Test
    void deveAtualizarPontoComSucesso() {
        when(repository.findById(PONTO_ID)).thenReturn(pontoEletronicoOptional);
        when(atendenteService.buscarNoBanco(any(Long.class))).thenReturn(atendente);

        pontoEletronicoService.atualizar(PONTO_ID, pontoRequest);

        verify(repository, times(1)).findById(PONTO_ID);
        verify(adapter, times(1)).pontoToHistoricoResponse(pontoEletronicoOptional.get());
    }

    @Test
    void deveFalharAoAtualziarPonto_quandoIdForNulo() {
        assertThrows(ValidacaoNegocioException.class, () -> pontoEletronicoService.atualizar(null, pontoRequest));
    }

    @Test
    void deveFazerDelecaoFisicaComSucesso() {
        doNothing().when(repository).deleteById(PONTO_ID);

        pontoEletronicoService.deletar(PONTO_ID);

        verify(repository, times(1)).deleteById(PONTO_ID);
    }

    @Test
    void deveFalharAoTentarDelecaoFisica_quandoIdForNull() {
        assertThrows(ValidacaoNegocioException.class, () -> pontoEletronicoService.deletar(null));
    }

    @Test
    void deveBuscarPontoComSucesso() {
        when(repository.findById(PONTO_ID)).thenReturn(pontoEletronicoOptional);

        pontoEletronicoService.buscar(PONTO_ID);

        verify(repository, times(1)).findById(PONTO_ID);
        verify(adapter, times(1)).pontoToHistoricoResponse(pontoEletronicoOptional.get());
    }

    @Test
    void deveFalharAoBuscarPonto_quandoIdForNull() {
        assertThrows(ValidacaoNegocioException.class, () -> pontoEletronicoService.buscar(null));
    }
}