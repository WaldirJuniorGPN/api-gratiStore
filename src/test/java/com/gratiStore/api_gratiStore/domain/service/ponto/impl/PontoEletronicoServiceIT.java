package com.gratiStore.api_gratiStore.domain.service.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.PontoResponse;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.domain.utils.AtestadoUtils;
import com.gratiStore.api_gratiStore.domain.utils.DescontarEmHorasUtils;
import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;
import com.gratiStore.api_gratiStore.domain.utils.FolgaUtils;
import com.gratiStore.api_gratiStore.infra.config.TestContainerConfig;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils.NAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PontoEletronicoServiceIT extends TestContainerConfig {

    private final LocalDate DATA = LocalDate.of(2025, 6, 1);
    private final LocalTime ENTRADA = LocalTime.of(8, 0);
    private final LocalTime INICIO_ALMOCO = LocalTime.of(11, 0);
    private final LocalTime FIM_ALMOCO = LocalTime.of(12, 0);
    private final LocalTime SAIDA = LocalTime.of(17, 0);

    @Autowired
    private AtendenteService atendenteService;

    @Autowired
    private LojaService lojaService;

    @Autowired
    private PontoEletronicoService pontoEletronicoService;

    private AtendenteResponse atendenteResponse;
    private PontoRequest pontoRequest;
    private Loja loja;
    private PontoResponse pontoResponse;

    @BeforeEach
    void setUp() {
        loja = new Loja("Americanas", "06026378000140");
        lojaService.salvarNoBanco(loja);
        var atendenteRequest = new AtendenteRequest("Fulano", loja.getId(), BigDecimal.valueOf(3000));
        atendenteResponse = atendenteService.criar(atendenteRequest);
        pontoRequest = new PontoRequest(DATA,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendenteResponse.id());
        pontoResponse = pontoEletronicoService.registrarPonto(pontoRequest);
    }

    @Test
    void deveRegistrarPonto() {

        assertEquals(atendenteResponse.id(), pontoResponse.id());
    }

    @Test
    void deveListarHistorico_comRetornoPaginado() {

        var pageable = PageRequest.of(1, 10);
        var response = pontoEletronicoService.listarHistorico(pageable);

        assertEquals(1, response.getTotalElements());
    }

    @Test
    void deveListarHistorico_comRetornoNaoPaginado() {
        var atendentes = loja.getAtendentes();
        var pontos = pontoEletronicoService.listarHistorico(atendentes, 6, 2025);

        assertEquals(1, pontos.size());
        assertEquals(pontoRequest.data(), pontos.get(0).getData());
    }

    @Test
    void deveAtualizarPontoEletronico_comSucesso() {
        var pontoId = pontoResponse.id();
        var pontoRequestAtualizado = new PontoRequest(DATA,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA.plusHours(1),
                FeriadoUtils.NAO,
                AtestadoUtils.NAO,
                FolgaUtils.NAO,
                DescontarEmHorasUtils.NAO,
                atendenteResponse.id()
        );

        var resultado = pontoEletronicoService.atualizar(pontoId, pontoRequestAtualizado);

        assertEquals(pontoResponse.saida().plusHours(1), resultado.saida());
    }

    @Test
    void deveFalharAoAtualizarPontoEletronico_quandoIdNaoEncontrado() {
        var idInvalido = 2222L;
        var msgError = "Ponto Eletrônico com id %d não encontrado";
        var exception = assertThrows(EntityNotFoundException.class, () -> pontoEletronicoService.atualizar(idInvalido, pontoRequest));
        assertEquals(String.format(msgError, idInvalido), exception.getMessage());
    }

    @Test
    void deveBuscarPontoEletronico_comSucesso() {
        var historicoResponse = new HistoricoResponse(pontoResponse.id(),
                pontoResponse.data(),
                pontoResponse.entrada(),
                pontoResponse.incioAlmoco(),
                pontoResponse.fimAlmoco(),
                pontoResponse.saida(),
                pontoResponse.feriado(),
                atendenteResponse.id());
        var resultado = pontoEletronicoService.buscar(pontoResponse.id());

        assertEquals(historicoResponse, resultado);
    }

    @Test
    void deveFalharAoBuscarPontoEletronico_quandoIdNaoEncontrado() {
        var idInvalido = 2222l;
        assertThrows(EntityNotFoundException.class, () -> pontoEletronicoService.buscar(idInvalido));
    }

    @Test
    void deveFazerDelecaoFisica_comSucesso() {
        pontoEletronicoService.deletar(pontoResponse.id());
        assertThrows(EntityNotFoundException.class, () -> pontoEletronicoService.buscar(pontoResponse.id()));
    }
}
