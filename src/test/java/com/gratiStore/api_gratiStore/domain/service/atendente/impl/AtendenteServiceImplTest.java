package com.gratiStore.api_gratiStore.domain.service.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestVendas;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtrasoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponseVendas;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import com.gratiStore.api_gratiStore.infra.repository.AtendenteRepository;
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
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.NAO;
import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.SIM;
import static com.gratiStore.api_gratiStore.domain.utils.SemanaUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtendenteServiceImplTest {

    @InjectMocks
    private AtendenteServiceImpl atendenteService;

    @Mock
    private LojaService lojaService;

    @Mock
    private AtendenteAdapter adapter;

    @Mock
    private AtendenteRepository repository;

    private Atendente atendenteMock;
    private AtendenteRequest request;
    private AtendenteResponse response;
    private Loja loja;
    private AtendenteRequestPlanilha planilhaRequest;
    private SemanaUtils semana;
    private List<Atendente> atendenteList;

    @BeforeEach
    void setUp() {
        this.loja = new Loja("Americanas", "06026378000140");
        this.atendenteMock = new Atendente("Fulano", loja, new BigDecimal("2000"));
        this.request = new AtendenteRequest("Fulano", 1L, new BigDecimal("2000"));
        this.response = new AtendenteResponse(1L, "Fulano", "Americana");
        this.planilhaRequest = new AtendenteRequestPlanilha("Fulano", 10, BigDecimal.valueOf(100), 1L);
        this.atendenteList = List.of(atendenteMock, atendenteMock);
    }

    @Test
    void deveCriarAtendente_quandoRequestValido() {
        when(adapter.atendenteRequestToAtendente(request)).thenReturn(atendenteMock);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteToAtendenteResponse(atendenteMock)).thenReturn(response);

        var resultado = atendenteService.criar(request);

        assertEquals("Fulano", resultado.nome());
        verify(adapter, times(1)).atendenteRequestToAtendente(request);
        verify(repository, times(1)).save(any(Atendente.class));
        verify(adapter, times(1)).atendenteToAtendenteResponse(any(Atendente.class));
    }

    @Test
    void deveAtualizarPrimeiraSemana_quandoAtendenteJaExiste() {

        semana = PRIMEIRA;

        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals(10, atendenteMock.getQuantidadeAtendimentosPrimeiraSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasPrimeiraSemana());
        verifyNoInteractions(adapter);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarPrimeiraSemana_quandoAtendenteNaoExiste() {

        semana = PRIMEIRA;

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteRequestToAtendente(planilhaRequest)).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals("Fulano", atendenteMock.getNome());
        assertEquals(new BigDecimal("2000.00"), atendenteMock.getSalario());
        assertEquals(10, atendenteMock.getQuantidadeAtendimentosPrimeiraSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasPrimeiraSemana());
        verify(adapter, times(1)).atendenteRequestToAtendente(planilhaRequest);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarSegundaSemana_quandoAtendenteNaoExiste() {

        semana = SEGUNDA;

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteRequestToAtendente(planilhaRequest)).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals("Fulano", atendenteMock.getNome());
        assertEquals(new BigDecimal("2000.00"), atendenteMock.getSalario());
        assertEquals(10, atendenteMock.getQuantidadeAtendimentosSegundaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasSegundaSemana());
        verify(adapter, times(1)).atendenteRequestToAtendente(planilhaRequest);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarSegundaSemana_quandoAtendenteJaExiste() {

        semana = SEGUNDA;

        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals(10, atendenteMock.getQuantidadeAtendimentosSegundaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasSegundaSemana());
        verifyNoInteractions(adapter);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarTerceiraSemana_quandoAtendenteNaoExiste() {

        semana = TERCEIRA;

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteRequestToAtendente(planilhaRequest)).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals("Fulano", atendenteMock.getNome());
        assertEquals(new BigDecimal("2000.00"), atendenteMock.getSalario());
        assertEquals(10, atendenteMock.getQuantidadeAtendimentosTerceiraSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasTerceiraSemana());
        verify(adapter, times(1)).atendenteRequestToAtendente(planilhaRequest);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarTerceiraSemana_quandoAtendenteJaExiste() {

        semana = TERCEIRA;

        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals(10, atendenteMock.getQuantidadeAtendimentosTerceiraSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasTerceiraSemana());
        verifyNoInteractions(adapter);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarQuartaSemana_quandoAtendenteNaoExiste() {

        semana = QUARTA;

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteRequestToAtendente(planilhaRequest)).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals("Fulano", atendenteMock.getNome());
        assertEquals(new BigDecimal("2000.00"), atendenteMock.getSalario());
        assertEquals(10, atendenteMock.getQuantidadeAtendimentosQuartaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasQuartaSemana());
        verify(adapter, times(1)).atendenteRequestToAtendente(planilhaRequest);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarQuartaSemana_quandoAtendenteJaExiste() {

        semana = QUARTA;

        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals(10, atendenteMock.getQuantidadeAtendimentosQuartaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasQuartaSemana());
        verifyNoInteractions(adapter);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarQuintaSemana_quandoAtendenteNaoExiste() {

        semana = QUINTA;

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteRequestToAtendente(planilhaRequest)).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals("Fulano", atendenteMock.getNome());
        assertEquals(new BigDecimal("2000.00"), atendenteMock.getSalario());
        assertEquals(10, atendenteMock.getQuantidadeAtendimentosQuintaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasQuintaSemana());
        verify(adapter, times(1)).atendenteRequestToAtendente(planilhaRequest);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarQuintaSemana_quandoAtendenteJaExiste() {

        semana = QUINTA;

        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals(10, atendenteMock.getQuantidadeAtendimentosQuintaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasQuintaSemana());
        verifyNoInteractions(adapter);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarSextaSemana_quandoAtendenteNaoExiste() {

        semana = SEXTA;

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);
        when(adapter.atendenteRequestToAtendente(planilhaRequest)).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals("Fulano", atendenteMock.getNome());
        assertEquals(new BigDecimal("2000.00"), atendenteMock.getSalario());
        assertEquals(10, atendenteMock.getQuantidadeAtendimentosSextaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasSextaSemana());
        verify(adapter, times(1)).atendenteRequestToAtendente(planilhaRequest);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveAtualizarSextaSemana_quandoAtendenteJaExiste() {

        semana = SEXTA;

        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(request.lojaId())).thenReturn(loja);
        when(repository.save(any(Atendente.class))).thenReturn(atendenteMock);

        atendenteService.uploadSemana(planilhaRequest, semana);

        assertEquals(10, atendenteMock.getQuantidadeAtendimentosSextaSemana());
        assertEquals(new BigDecimal("100.00"), atendenteMock.getVendasSextaSemana());
        verifyNoInteractions(adapter);
        verify(repository, times(1)).save(any(Atendente.class));
    }

    @Test
    void deveFalharAoAtualizar_quandoSemanaInvalida() {
        loja.getAtendentes().add(atendenteMock);

        when(lojaService.buscarLoja(any(Long.class))).thenReturn(loja);

        assertThrows(IllegalArgumentException.class, () -> atendenteService.uploadSemana(planilhaRequest, null));
    }

    @Test
    void deveAtualizarAtendente_quandoRequestValido() {
        var atualizacaoRequest = new AtendenteRequest("Beltrano", 1L, BigDecimal.valueOf(3000));
        var atendenteAtualizado = new Atendente("Beltrano", loja, BigDecimal.valueOf(3000));

        when(repository.findByIdAndAtivoTrue(any(Long.class))).thenReturn(Optional.of(atendenteMock));
        when(adapter.atendenteRequestToAtendente(atendenteMock, atualizacaoRequest)).thenReturn(atendenteAtualizado);

        atendenteService.atualizar(1L, atualizacaoRequest);

        verify(repository, times(1)).findByIdAndAtivoTrue(atualizacaoRequest.lojaId());
        verify(adapter, times(1)).atendenteRequestToAtendente(any(Atendente.class), any(AtendenteRequest.class));
        verify(repository, times(1)).save(any(Atendente.class));
        verify(adapter, times(1)).atendenteToAtendenteResponse(any(Atendente.class));
    }

    @Test
    void deveBuscarUmAtendente_quandoIdValido() {
        var atendenteId = 1L;

        when(repository.findByIdAndAtivoTrue(any(Long.class))).thenReturn(Optional.of(atendenteMock));

        atendenteService.buscar(atendenteId);

        verify(repository, times(1)).findByIdAndAtivoTrue(atendenteId);
        verify(adapter, times(1)).atendenteToAtendenteResponse(any(Atendente.class));
    }

    @Test
    void deveRetornarTodosAtendentesPaginado_quandoAtivo() {
        var pageable = PageRequest.of(1, 10);
        var page = new PageImpl<>(atendenteList);
        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(Optional.of(page));
        when(adapter.atendenteToAtendenteResponse(any(Atendente.class))).thenReturn(response);

        atendenteService.listarTodos(pageable);

        verify(repository, times(1)).findAllByAtivoTrue(pageable);
        verify(adapter, times(atendenteList.size())).atendenteToAtendenteResponse(any(Atendente.class));
    }

    @Test
    void deveRetornarTodosAtendentesEmLista_quandoAtivo() {
        when(repository.findAllByAtivoTrue()).thenReturn(atendenteList);
        when(adapter.atendenteToAtendenteResponse(any(Atendente.class))).thenReturn(response);

        atendenteService.listarTodos();

        verify(repository, times(1)).findAllByAtivoTrue();
        verify(adapter, times(atendenteList.size())).atendenteToAtendenteResponse(any(Atendente.class));
    }

    @Test
    void deveFazerExclusaoLogica() {
        var atendenteId = 1L;
        var nomeAntigo = atendenteMock.getNome();
        var loja = atendenteMock.getLoja();
        var zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        when(repository.findByIdAndAtivoTrue(atendenteId)).thenReturn(Optional.of(atendenteMock));

        atendenteService.deletar(atendenteId);

        verify(repository, times(1)).save(any(Atendente.class));
        assertNotEquals(nomeAntigo, atendenteMock.getNome());
        assertFalse(atendenteMock.isAtivo());
        assertFalse(loja.getAtendentes().contains(atendenteMock));
        assertNull(atendenteMock.getLoja());
        assertAll("Verificando valores zerados e resetados",
                () -> assertEquals(zero, atendenteMock.getVendasPrimeiraSemana()),
                () -> assertEquals(zero, atendenteMock.getVendasSegundaSemana()),
                () -> assertEquals(zero, atendenteMock.getVendasTerceiraSemana()),
                () -> assertEquals(zero, atendenteMock.getVendasQuartaSemana()),
                () -> assertEquals(zero, atendenteMock.getVendasQuintaSemana()),
                () -> assertEquals(zero, atendenteMock.getVendasSextaSemana()),
                () -> assertEquals(zero, atendenteMock.getBonus()),
                () -> assertEquals(zero, atendenteMock.getGratificacao()),
                () -> assertEquals(zero, atendenteMock.getTotalVendas()),
                () -> assertEquals(0, atendenteMock.getQuantidadeAtendimentosPrimeiraSemana()),
                () -> assertEquals(0, atendenteMock.getQuantidadeAtendimentosSegundaSemana()),
                () -> assertEquals(0, atendenteMock.getQuantidadeAtendimentosTerceiraSemana()),
                () -> assertEquals(0, atendenteMock.getQuantidadeAtendimentosQuartaSemana()),
                () -> assertEquals(0, atendenteMock.getQuantidadeAtendimentosSextaSemana()),
                () -> assertEquals(NAO, atendenteMock.getAtrasoStatusPrimeiraSemana()),
                () -> assertEquals(NAO, atendenteMock.getAtrasoStatusSegundaSemana()),
                () -> assertEquals(NAO, atendenteMock.getAtrasoStatusQuartaSemana()),
                () -> assertEquals(NAO, atendenteMock.getAtrasoStatusQuintaSemana()),
                () -> assertEquals(NAO, atendenteMock.getAtrasoStatusSextaSemana()));
    }

    @Test
    void deveAdicionarVenda() {
        var request = new AtendenteRequestVendas(
                BigDecimal.valueOf(100),
                SIM,
                BigDecimal.valueOf(200),
                SIM,
                BigDecimal.valueOf(300),
                SIM,
                BigDecimal.valueOf(400),
                SIM,
                BigDecimal.valueOf(500),
                SIM,
                BigDecimal.valueOf(600),
                SIM,
                10,
                20,
                30,
                40,
                50,
                60
        );
        var atendenteId = 1L;
        var responseVendas = new AtendenteResponseVendas(atendenteMock);
        when(repository.findByIdAndAtivoTrue(any(Long.class))).thenReturn(Optional.of(atendenteMock));
        when(adapter.atendenteToAtendenteResponseVendas(any(Atendente.class))).thenReturn(responseVendas);

        atendenteService.adicionarVendas(atendenteId, request);

        verify(adapter, times(1)).atendenteToAtendenteResponseVendas(any(Atendente.class));
        verify(repository, times(1)).findByIdAndAtivoTrue(any(Long.class));
        verify(repository, times(1)).save(any(Atendente.class));
        assertAll("Verifica valores setados",
                () -> assertEquals(request.vendasPrimeiraSemana().setScale(2, RoundingMode.HALF_UP), atendenteMock.getVendasPrimeiraSemana()),
                () -> assertEquals(request.vendasSegundaSemana().setScale(2, RoundingMode.HALF_UP), atendenteMock.getVendasSegundaSemana()),
                () -> assertEquals(request.vendasTerceiraSemana().setScale(2, RoundingMode.HALF_UP), atendenteMock.getVendasTerceiraSemana()),
                () -> assertEquals(request.vendasQuartaSemana().setScale(2, RoundingMode.HALF_UP), atendenteMock.getVendasQuartaSemana()),
                () -> assertEquals(request.vendasQuintaSemana().setScale(2, RoundingMode.HALF_UP), atendenteMock.getVendasQuintaSemana()),
                () -> assertEquals(request.vendasSextaSemana().setScale(2, RoundingMode.HALF_UP), atendenteMock.getVendasSextaSemana()),
                () -> assertEquals(request.atrasoPrimeiraSemana(), atendenteMock.getAtrasoStatusPrimeiraSemana()),
                () -> assertEquals(request.atrasoSegundaSemana(), atendenteMock.getAtrasoStatusSegundaSemana()),
                () -> assertEquals(request.atrasoTerceiraSemana(), atendenteMock.getAtrasoStatusTerceiraSemana()),
                () -> assertEquals(request.atrasoQuartaSemana(), atendenteMock.getAtrasoStatusQuartaSemana()),
                () -> assertEquals(request.atrasoQuintaSemana(), atendenteMock.getAtrasoStatusQuintaSemana()),
                () -> assertEquals(request.atrasoSextaSemana(), atendenteMock.getAtrasoStatusSextaSemana()),
                () -> assertEquals(request.quantidadeAtendimentosPrimeiraSemana(), atendenteMock.getQuantidadeAtendimentosPrimeiraSemana()),
                () -> assertEquals(request.quantidadeAtendimentosSegundaSemana(), atendenteMock.getQuantidadeAtendimentosSegundaSemana()),
                () -> assertEquals(request.quantidadeAtendimentosTerceiraSemana(), atendenteMock.getQuantidadeAtendimentosTerceiraSemana()),
                () -> assertEquals(request.quantidadeAtendimentosQuartaSemana(), atendenteMock.getQuantidadeAtendimentosQuartaSemana()),
                () -> assertEquals(request.quantidadeAtendimentosQuintaSemana(), atendenteMock.getQuantidadeAtendimentosQuintaSemana()),
                () -> assertEquals(request.quantidadeAtendimentosSextaSemana(), atendenteMock.getQuantidadeAtendimentosSextaSemana()));
    }

    @Test
    void deveAlterarStatusDeAtraso_daPrimeiraSemana() {
        var request = new AtrasoRequest(1L, SIM, PRIMEIRA);
        when(repository.findByIdAndAtivoTrue(request.id())).thenReturn(Optional.of(atendenteMock));

        atendenteService.updateAtraso(request);

        assertEquals(request.atraso(), atendenteMock.getAtrasoStatusPrimeiraSemana());
        verify(repository, times(1)).findByIdAndAtivoTrue(request.id());
        verify(repository, times(1)).save(atendenteMock);
        verify(adapter, times(1)).atendenteToAtrasoResponse(atendenteMock, request);
    }

    @Test
    void deveAlterarStatusDeAtraso_daSegundaSemana() {
        var request = new AtrasoRequest(1L, SIM, SEGUNDA);
        when(repository.findByIdAndAtivoTrue(request.id())).thenReturn(Optional.of(atendenteMock));

        atendenteService.updateAtraso(request);

        assertEquals(request.atraso(), atendenteMock.getAtrasoStatusSegundaSemana());
        verify(repository, times(1)).findByIdAndAtivoTrue(request.id());
        verify(repository, times(1)).save(atendenteMock);
        verify(adapter, times(1)).atendenteToAtrasoResponse(atendenteMock, request);
    }

    @Test
    void deveAlterarStatusDeAtraso_daTerceiraSemana() {
        var request = new AtrasoRequest(1L, SIM, TERCEIRA);
        when(repository.findByIdAndAtivoTrue(request.id())).thenReturn(Optional.of(atendenteMock));

        atendenteService.updateAtraso(request);

        assertEquals(request.atraso(), atendenteMock.getAtrasoStatusTerceiraSemana());
        verify(repository, times(1)).findByIdAndAtivoTrue(request.id());
        verify(repository, times(1)).save(atendenteMock);
        verify(adapter, times(1)).atendenteToAtrasoResponse(atendenteMock, request);
    }

    @Test
    void deveAlterarStatusDeAtraso_daQuartaSemana() {
        var request = new AtrasoRequest(1L, SIM, QUARTA);
        when(repository.findByIdAndAtivoTrue(request.id())).thenReturn(Optional.of(atendenteMock));

        atendenteService.updateAtraso(request);

        assertEquals(request.atraso(), atendenteMock.getAtrasoStatusQuartaSemana());
        verify(repository, times(1)).findByIdAndAtivoTrue(request.id());
        verify(repository, times(1)).save(atendenteMock);
        verify(adapter, times(1)).atendenteToAtrasoResponse(atendenteMock, request);
    }

    @Test
    void deveAlterarStatusDeAtraso_daQuintaSemana() {
        var request = new AtrasoRequest(1L, SIM, QUINTA);
        when(repository.findByIdAndAtivoTrue(request.id())).thenReturn(Optional.of(atendenteMock));

        atendenteService.updateAtraso(request);

        assertEquals(request.atraso(), atendenteMock.getAtrasoStatusQuintaSemana());
        verify(repository, times(1)).findByIdAndAtivoTrue(request.id());
        verify(repository, times(1)).save(atendenteMock);
        verify(adapter, times(1)).atendenteToAtrasoResponse(atendenteMock, request);
    }

    @Test
    void deveAlterarStatusDeAtraso_daSextaSemana() {
        var request = new AtrasoRequest(1L, SIM, SEXTA);
        when(repository.findByIdAndAtivoTrue(request.id())).thenReturn(Optional.of(atendenteMock));

        atendenteService.updateAtraso(request);

        assertEquals(request.atraso(), atendenteMock.getAtrasoStatusSextaSemana());
        verify(repository, times(1)).findByIdAndAtivoTrue(request.id());
        verify(repository, times(1)).save(atendenteMock);
        verify(adapter, times(1)).atendenteToAtrasoResponse(atendenteMock, request);
    }
}