package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculadoraDeHorasExtrasImpl implements CalculadoraDeHorasExtras {

    private final Duration JORNADA_DIARIA = Duration.ofHours(8);
    private final Duration JORNADA_SEMANAL = Duration.ofHours(44);


    @Override
    public Duration calcular(Map<Integer, List<PontoEletronico>> pontos) {
        var resultadoSemanal = Duration.ZERO;
        var resultadoDiario = Duration.ZERO;

        var horasExtrasSemanais = new HashMap<Integer, Duration>();
        var horasExtrasDiarias = new HashMap<Integer, Duration>();

        for (var entry : pontos.entrySet()) {
            var semana = entry.getKey();
            var pontosDaSemana = entry.getValue();

            var semanal = calcularHorasSemanais(pontosDaSemana);
            var diario = calcularHorasExtrasDiarias(pontosDaSemana);

            resultadoSemanal = resultadoSemanal.plus(semanal);
            resultadoDiario = resultadoDiario.plus(diario);

            horasExtrasSemanais.put(semana, semanal);
            horasExtrasDiarias.put(semana, diario);
        }

        return compararHorasExtras(horasExtrasDiarias, horasExtrasSemanais);
    }

    private Duration compararHorasExtras(Map<Integer, Duration> horasExtrasDiarias, Map<Integer, Duration> horasExtrasSemanais) {
        var maiores = new HashMap<Integer, Duration>();

        for (Integer semana : horasExtrasSemanais.keySet()) {
            if (horasExtrasSemanais.containsKey(semana)) {
                var diaria = horasExtrasDiarias.get(semana);
                var semanal = horasExtrasSemanais.get(semana);
                var maior = diaria.compareTo(semanal) > 0 ? diaria : semanal;
                maiores.put(semana, maior);
            }
        }

        return maiores.values()
                .stream()
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Duration calcularCargaHorariaDoDia(PontoEletronico ponto) {
        var manha = Duration.between(ponto.getEntrada(), ponto.getInicioAlmoco());
        var tarde = Duration.between(ponto.getFimAlmoco(), ponto.getSaida());

        return manha.plus(tarde);
    }

    private Duration calcularHorasSemanais(List<PontoEletronico> pontos) {
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
