package com.gratiStore.api_gratiStore.domain.service.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.config.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils.NAO;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PontoEletroncioServiceIT extends TestContainerConfig {

    @Autowired
    private AtendenteService atendenteService;

    @Autowired
    private LojaService lojaService;

    @Autowired
    private PontoEletronicoService pontoEletronicoService;

    private AtendenteResponse atendenteResponse;
    private PontoRequest pontoRequest;
    private Loja loja;

    @BeforeEach
    void setUp() {
        loja = new Loja("Americanas", "06026378000140");
        lojaService.salvarNoBanco(loja);
        var atendenteRequest = new AtendenteRequest("Fulano", loja.getId(), BigDecimal.valueOf(3000));
        atendenteResponse = atendenteService.criar(atendenteRequest);
        pontoRequest = new PontoRequest(LocalDate.of(2025, 6, 1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(17, 0),
                NAO,
                atendenteResponse.id());
    }

    @Test
    void deveRegistrarPonto() {
        var resultado = pontoEletronicoService.registrarPonto(pontoRequest);

        assertEquals(atendenteResponse.id(), resultado.id());
    }

    @Test
    void deveListarHistorico_comRetornoPaginado() {
        pontoEletronicoService.registrarPonto(pontoRequest);

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
}
