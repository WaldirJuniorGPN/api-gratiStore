package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.validator.integridadeDeEntrada.Validator.validarHistoricoDePonto;

@Service
public class CalculadoraDeHorasExtrasImpl implements CalculadoraDeHorasExtras {

    private final Duration JORNADA_DIARIA = Duration.ofHours(8);

    @Override
    public Duration calcular(List<PontoEletronico> pontos) {
        validarHistoricoDePonto(pontos);
        return pontos.stream()
                .map(this::calcularHorasExtrasDiarias)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Duration calcularHorasExtrasDiarias(PontoEletronico ponto) {
        var manha = Duration.between(ponto.getEntrada(), ponto.getInicioAlmoco());
        var tarde = Duration.between(ponto.getFimAlmoco(), ponto.getSaida());
        var totalDiario = manha.plus(tarde);

        return totalDiario.minus(JORNADA_DIARIA);
    }
}
