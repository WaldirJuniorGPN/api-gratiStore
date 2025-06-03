package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.RegistroHorasExtras;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class CalculadoraDeHorasExtrasImpl implements CalculadoraDeHorasExtras {

    private final Duration JORNADA_DIARIA = Duration.ofHours(8);
    private final Duration JORNADA_SEMANAL = Duration.ofHours(44);


    @Override
    public Duration calcular(Map<Integer, List<PontoEletronico>> pontos) {

        List<RegistroHorasExtras> registros = pontos.entrySet().stream()
                .map(entry -> new RegistroHorasExtras(
                        entry.getKey(),
                        calcularHorasExtrasDiarias(entry.getValue()),
                        calcularHorasExtrasSemanais(entry.getValue())
                ))
                .toList();

        return registros.stream()
                .map(r -> escolherMaior(r.diaria(), r.semanal()))
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Duration escolherMaior(Duration diaria, Duration semanal) {
        return diaria.compareTo(semanal) > 0 ? diaria : semanal;
    }

    private Duration calcularCargaHorariaDoDia(PontoEletronico ponto) {
        var manha = Duration.between(ponto.getEntrada(), ponto.getInicioAlmoco());
        var tarde = Duration.between(ponto.getFimAlmoco(), ponto.getSaida());

        return manha.plus(tarde);
    }

    private Duration calcularHorasExtrasSemanais(List<PontoEletronico> pontos) {
        var totalHorasDiaras = pontos.stream()
                .map(this::calcularCargaHorariaDoDia)
                .reduce(Duration.ZERO, Duration::plus);

        return totalHorasDiaras.minus(JORNADA_SEMANAL);
    }

    private Duration calcularHorasExtrasDiarias(List<PontoEletronico> pontos) {
        return pontos.stream()
                .map(this::calcularCargaHorariaDoDia)
                .map(duration -> duration.minus(JORNADA_DIARIA))
                .reduce(Duration.ZERO, Duration::plus);
    }
}
