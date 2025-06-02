package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResumoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.HorasExtrasService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorasExtrasServiceImpl implements HorasExtrasService {

    private final PontoEletronicoService pontoEletronicoService;
    private final CalculadoraDeHorasExtras calculadora;
    private final AgrupadorDePontosPorSemana agrupadorDePontosPorSemana;

    @Override
    public List<ResumoHorasExtrasResponse> calcular(FiltroHorasExtrasRequest request) {
        var pontosEletronicos = pontoEletronicoService.listarHistorico(request);
        var pontosAgrupados = agrupadorDePontosPorSemana.agrupar(pontosEletronicos);
        var totalHorasExtras = calculadora.calcular(pontosAgrupados);

        return pontosEletronicos.stream()
                .map(ponto -> new ResumoHorasExtrasResponse(ponto.getAtendente().getId(), totalHorasExtras))
                .toList();
    }
}
