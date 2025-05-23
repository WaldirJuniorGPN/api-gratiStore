package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResumoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorasExtrasServiceImplTest {

    @Mock
    private PontoEletronicoService pontoEletronicoService;

    @Mock
    private CalculadoraDeHorasExtras calculadora;

    @InjectMocks
    private HorasExtrasServiceImpl horasExtrasService;

    private ResumoHorasExtrasResponse response;
    private FiltroHorasExtrasRequest request;
    private PontoEletronico pontoEletronico;
    private List<PontoEletronico> pontoEletronicoList;
    private Atendente atendente;
    private Loja loja;

    @BeforeEach
    void setUp() {
        response = new ResumoHorasExtrasResponse(1L, Duration.ofHours(2L));
        request = new FiltroHorasExtrasRequest(1, 2025, 1L);
        loja = new Loja("Strategy", "06026378000140");
        atendente = new Atendente("Fulano", loja, BigDecimal.valueOf(20000));
        pontoEletronico = new PontoEletronico(
                LocalDate.now().minusDays(1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(18, 0),
                atendente
        );
        pontoEletronicoList = List.of(pontoEletronico, pontoEletronico);
    }

    @Test
    void deveCalcularHorasExtras() {
        var horasExtras = Duration.ofHours(2L);
        var responseList = List.of(response, response);
        when(pontoEletronicoService.listarHistorico(request)).thenReturn(pontoEletronicoList);
        when(calculadora.calcular(pontoEletronicoList)).thenReturn(horasExtras);

        var resultado = horasExtrasService.calcular(request);

        assertEquals(responseList.size(), resultado.size());

        IntStream.range(0, resultado.size())
                        .forEach(i -> assertEquals(responseList.get(i).totalHorasExtras(), resultado.get(i).totalHorasExtras()));

        verify(pontoEletronicoService, times(1)).listarHistorico(request);
        verify(calculadora, times(1)).calcular(pontoEletronicoList);
    }
}