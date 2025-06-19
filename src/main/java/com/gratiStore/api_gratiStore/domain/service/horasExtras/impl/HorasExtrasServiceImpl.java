package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.HorasExtrasService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.repository.ResultadoHoraExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HorasExtrasServiceImpl implements HorasExtrasService {

    private static final BigDecimal DIAS_DO_MES = BigDecimal.valueOf(30);
    private static final BigDecimal JORNADA_DIARIA = BigDecimal.valueOf(8);
    private static final BigDecimal ADICIONAL_HORA_EXTRA = BigDecimal.valueOf(0.5);

    private final PontoEletronicoService pontoEletronicoService;
    private final CalculadoraDeHorasExtras calculadora;
    private final AgrupadorDePontosPorSemana agrupadorDePontosPorSemana;
    private final ResultadoHoraExtraRepository repository;

    private BigDecimal valorAReceber = BigDecimal.ZERO;

    @Override
    public List<ResultadoHorasExtrasResponse> calcular(FiltroHorasExtrasRequest request) {
        var pontosEletronicos = pontoEletronicoService.listarHistorico(request);
        var pontosAgrupados = agrupadorDePontosPorSemana.agrupar(pontosEletronicos);
        Map<Atendente, Duration> totalHorasExtrasPorAtendente = calculadora.calcular(pontosAgrupados);

        totalHorasExtrasPorAtendente.forEach((atendente, horasExtras) -> {
            valorAReceber = calcularValorAReceber(atendente.getSalario(), horasExtras);
            salvarNoBanco(request.mes(), request.ano(), atendente,valorAReceber, horasExtras);
        });

        return totalHorasExtrasPorAtendente.entrySet().stream()
                .map(entry ->
                        new ResultadoHorasExtrasResponse(
                                entry.getKey().getNome(),
                                request.mes(),
                                request.ano(),
                                valorAReceber,
                                entry.getValue()
                        ))
                .toList();
    }

    private void salvarNoBanco(int mes, int ano, Atendente atendente, BigDecimal valorAReceber, Duration horasExtras) {
        repository.save(new ResultadoHoraExtra(atendente, mes, ano, valorAReceber, horasExtras));
    }

    private BigDecimal calcularValorAReceber(BigDecimal salario, Duration horasExtras) {

        var salarioDia = salario.divide(DIAS_DO_MES, MathContext.DECIMAL64);
        var valorHora = salarioDia.divide(JORNADA_DIARIA, MathContext.DECIMAL64);
        var valorHoraExtra = valorHora.add(valorHora.multiply(ADICIONAL_HORA_EXTRA, MathContext.DECIMAL64));

        return valorHoraExtra.multiply(BigDecimal.valueOf(horasExtras.toHours()))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
