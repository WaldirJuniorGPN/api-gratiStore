package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;


import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
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
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.COMUM;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class HorasExtrasServiceImplIT extends TestContainerConfig {

    private final LocalDate DATA = LocalDate.of(2025, 6, 2);
    private final LocalTime ENTRADA = LocalTime.of(9, 0);
    private final LocalTime INICIO_ALMOCO = LocalTime.of(11, 0);
    private final LocalTime FIM_ALMOCO = LocalTime.of(12, 0);
    private final LocalTime SAIDA = LocalTime.of(21, 0);

    private FiltroHorasExtrasRequest filtroHorasExtrasRequest;
    private ResultadoHorasExtrasResponse resultadoHorasExtrasResponse;
    private AtendenteResponse atendenteResponse;
    private Loja loja;

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
        this.loja = new Loja("Google", "06026378000140");
        lojaService.salvarNoBanco(loja);

        var atendenteRequest = new AtendenteRequest("Fulano", loja.getId(), BigDecimal.valueOf(2000));
        this.atendenteResponse = atendenteService.criar(atendenteRequest);

        var pontoRequest = new PontoRequest(DATA,
                ENTRADA,
                INICIO_ALMOCO,
                FIM_ALMOCO,
                SAIDA,
                COMUM,
                atendenteResponse.id());
        pontoEletronicoService.registrarPonto(pontoRequest);

        filtroHorasExtrasRequest = new FiltroHorasExtrasRequest(DATA.getMonthValue(), DATA.getYear(), loja.getId());

        var valorAReceber50 = BigDecimal.valueOf(25).setScale(2, RoundingMode.HALF_UP);
        var valorAReceber100 = BigDecimal.valueOf(16.666).setScale(2, RoundingMode.HALF_UP);
        var horasExtras50 = Duration.ofHours(2);
        var horasExtras100 = Duration.ofHours(1);
        resultadoHorasExtrasResponse = new ResultadoHorasExtrasResponse(atendenteRequest.nome(),
                DATA.getMonthValue(),
                DATA.getYear(),
                valorAReceber50,
                valorAReceber100,
                horasExtras50,
                horasExtras100);
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

    @Test
    void deveCalcularHorasExtras_quandoAsHorasSemanaisForemMaiores() {
        var entrada = LocalTime.of(9,0);
        var inicioAlmoco = LocalTime.of(11,0);
        var fimAlmoco = LocalTime.of(12,0);
        var saida = LocalTime.of(18,0);

        var pontoRequest1 = new PontoRequest(LocalDate.of(2025, 8, 4),
                entrada,
                inicioAlmoco,
                fimAlmoco,
                saida,
                COMUM,
                this.atendenteResponse.id());

        var pontoRequest2 = new PontoRequest(LocalDate.of(2025, 8, 5),
                entrada,
                inicioAlmoco,
                fimAlmoco,
                saida,
                COMUM,
                this.atendenteResponse.id());

        var pontoRequest3 = new PontoRequest(LocalDate.of(2025, 8, 6),
                entrada,
                inicioAlmoco,
                fimAlmoco,
                saida,
                COMUM,
                this.atendenteResponse.id());

        var pontoRequest4 = new PontoRequest(LocalDate.of(2025, 8, 7),
                entrada,
                inicioAlmoco,
                fimAlmoco,
                saida,
                COMUM,
                this.atendenteResponse.id());

        var pontoRequest5 = new PontoRequest(LocalDate.of(2025, 8, 8),
                entrada,
                inicioAlmoco,
                fimAlmoco,
                saida,
                COMUM,
                this.atendenteResponse.id());

        var pontoRequest6 = new PontoRequest(LocalDate.of(2025, 8, 9),
                entrada,
                inicioAlmoco,
                fimAlmoco,
                saida,
                COMUM,
                this.atendenteResponse.id());

        var pontosRequestList = List.of(pontoRequest1,
                pontoRequest2,
                pontoRequest3,
                pontoRequest4,
                pontoRequest5,
                pontoRequest6);

        for (int i = 0; i < pontosRequestList.size(); i++) {
            pontoEletronicoService.registrarPonto(pontosRequestList.get(i));
        }

        var filtroRequest = new FiltroHorasExtrasRequest(8, 2025, loja.getId());

        var resultado = horasExtrasService.calcular(filtroRequest);

        assertEquals(Duration.ofHours(4), resultado.get(0).totalHorasExtras50PorCento());
        assertEquals(Duration.ZERO, resultado.get(0).totalHorasExtras100PorCento());
        assertEquals(BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP), resultado.get(0).valorAReceber50PorCento());
        assertEquals(BigDecimal.ZERO, resultado.get(0).valorAReceber100PorCento());
    }
}
