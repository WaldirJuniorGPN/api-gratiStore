package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;


import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.HorasExtrasService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.config.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.COMUM;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class HorasExtrasServiceImplIT extends TestContainerConfig {

    private final LocalDate DATA = LocalDate.of(2025, 6, 2);
    private final LocalTime ENTRADA = LocalTime.of(9, 0);
    private final LocalTime INICIO_ALMOCO = LocalTime.of(11, 0);
    private final LocalTime FIM_ALMOCO = LocalTime.of(12, 0);
    private final LocalTime SAIDA = LocalTime.of(19, 0);

    private FiltroHorasExtrasRequest filtroHorasExtrasRequest;
    private ResultadoHorasExtrasResponse resultadoHorasExtrasResponse;

    @Autowired
    private HorasExtrasService horasExtrasService;

    @Autowired
    private AtendenteService atendenteService;

    @Autowired
    private LojaService lojaService;

    @Autowired
    private PontoEletronicoService pontoEletronicoService;

    @BeforeEach
    void setUp() {
        var loja = new Loja("Google", "06026378000140");
        lojaService.salvarNoBanco(loja);

        var atendenteRequest = new AtendenteRequest("Fulano", loja.getId(), BigDecimal.valueOf(3000));
        var atendenteResponse = atendenteService.criar(atendenteRequest);

        var pontoRequest = new PontoRequest(DATA,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendenteResponse.id());
        pontoEletronicoService.registrarPonto(pontoRequest);

        filtroHorasExtrasRequest = new FiltroHorasExtrasRequest(DATA.getMonthValue(), DATA.getYear(), loja.getId());

        var valorAReceber = BigDecimal.valueOf(18.75).setScale(2, RoundingMode.HALF_UP);
        var totalHorasExtras = Duration.ofHours(1);
        resultadoHorasExtrasResponse = new ResultadoHorasExtrasResponse(atendenteRequest.nome(),
                DATA.getMonthValue(),
                DATA.getYear(),
                valorAReceber,
                totalHorasExtras);
    }

    @Test
    void deveCalcularHorasExtrasComSucesso() {

        var resultadoList = horasExtrasService.calcular(filtroHorasExtrasRequest);

        assertEquals(1, resultadoList.size());
        assertEquals(resultadoHorasExtrasResponse, resultadoList.get(0));
    }

    @Test
    void deveBuscarHorasExtrasComSucesso() {

        horasExtrasService.calcular(filtroHorasExtrasRequest);

        var resultadoList = horasExtrasService.buscar(filtroHorasExtrasRequest);

        assertEquals(1, resultadoList.size());
        assertEquals(resultadoHorasExtrasResponse, resultadoList.get(0));
    }
}
