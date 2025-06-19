package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.repository.ResultadoHoraExtraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HorasExtrasServiceImplTest {

    private final int MES = 5;
    private final int ANO = 2024;
    private final BigDecimal VALOR_A_RECEBER = BigDecimal.valueOf(18.75);
    private final Duration HORAS_EXTRAS = Duration.ofHours(2);
    private final long ID_LOJA = 1l;

    @Mock
    private PontoEletronicoService pontoEletronicoService;
    @Mock
    private CalculadoraDeHorasExtras calculadoraDeHorasExtras;
    @Mock
    private AgrupadorDePontosPorSemana agrupadorDePontosPorSemana;
    @Mock
    private ResultadoHoraExtraRepository repository;

    @InjectMocks
    private HorasExtrasServiceImpl horasExtrasService;

    private ResultadoHorasExtrasResponse resultadoHorasExtrasResponse;
    private List<ResultadoHorasExtrasResponse> resultadoHorasExtrasResponseList;
    private FiltroHorasExtrasRequest filtroHorasExtrasRequest;
    private Atendente atendente;
    private Loja loja;

    @BeforeEach
    void setUp() {
        loja = new Loja("Americanas", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(20000));
        filtroHorasExtrasRequest = new FiltroHorasExtrasRequest(MES, ANO, ID_LOJA);
        resultadoHorasExtrasResponse = new ResultadoHorasExtrasResponse(atendente.getNome(),
                MES,
                ANO,
                VALOR_A_RECEBER,
                HORAS_EXTRAS);
        resultadoHorasExtrasResponseList.add(resultadoHorasExtrasResponse);
    }

    @Test
    void deveCalcularHorasExtras_comSucesso() {

    }
}