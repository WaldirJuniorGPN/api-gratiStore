package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.utils.StatusUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.*;

@Service
public class CalculadoraDeHorasExtrasImpl implements CalculadoraDeHorasExtras {

    private static final Set<StatusUtils> STATUS_REMOVER = Set.of(ATESTADO_INTEGRAL, FOLGA, FALTA, DESCONTAR_EM_HORAS);
    private final Duration JORNADA_DOMINGO = Duration.ofHours(6);
    private final Duration JORNADA_NORMAL = Duration.ofHours(8);
    private final Duration JORNADA_SEMANAL = Duration.ofHours(44);
    private final BigDecimal DIAS_DO_MES = BigDecimal.valueOf(30);
    private final BigDecimal JORNADA_DIARIA = BigDecimal.valueOf(8);
    private final BigDecimal ADICIONAL_HORA_EXTRA_50_POR_CENTO = BigDecimal.valueOf(0.5);
    private final BigDecimal ADICIONAL_HORA_eXTRA_100_POR_CENTO = BigDecimal.valueOf(1);


    @Override
    public Map<Atendente, Duration> calcularHorasExtras(Map<Integer, List<PontoEletronico>> pontos) {
        var resultado = new HashMap<Atendente, Duration>();

        pontos.forEach((semana, pontosSemanais) -> {
            Map<Atendente, Duration> pontosAgrupados = pontosSemanais.stream()
                    .collect(Collectors.groupingBy(PontoEletronico::getAtendente))
                    .entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> {
                                var pontosDoAtendente = e.getValue();
                                var pontosFiltrados = filtrarPontos(pontosDoAtendente);
                                var horasDiarias = calcularHorasExtrasDiarias(pontosFiltrados);
                                var horasSemanais = calcularHorasExtrasSemanais(pontosFiltrados);
                                var resultadoPreliminar = escolherMaior(horasDiarias, horasSemanais);
                                var horasASeremDescontadas = somarHorasASeremDescontadas(pontosDoAtendente);
                                return resultadoPreliminar.minus(horasASeremDescontadas);
                            }
                    ));

            pontosAgrupados.forEach((atendente, duracao) -> {
                resultado.merge(atendente, duracao, Duration::plus);
            });
        });
        return resultado;
    }

    @Override
    public BigDecimal calcularValorAReceber(BigDecimal salario, Duration horasExtras) {
        var salarioDia = salario.divide(DIAS_DO_MES, MathContext.DECIMAL64);
        var valorHora = salarioDia.divide(JORNADA_DIARIA, MathContext.DECIMAL64);
        var valorHoraExtra = valorHora.add(valorHora.multiply(ADICIONAL_HORA_EXTRA_50_POR_CENTO, MathContext.DECIMAL64));
        var horasDecimais = BigDecimal.valueOf(horasExtras.getSeconds())
                .divide(BigDecimal.valueOf(3600), MathContext.DECIMAL64);
        var totalAReceber = valorHoraExtra.multiply(horasDecimais);

        return totalAReceber.setScale(2, RoundingMode.HALF_UP);
    }

    private Duration somarHorasASeremDescontadas(List<PontoEletronico> pontos) {
        var quantidadesDePontosADescontar = pontos.stream()
                .filter(ponto -> ponto.getStatus() == DESCONTAR_EM_HORAS)
                .count();

        return JORNADA_NORMAL.multipliedBy(quantidadesDePontosADescontar);
    }

    private Duration escolherMaior(Duration diaria, Duration semanal) {
        return diaria.compareTo(semanal) > 0 ? diaria : semanal;
    }

    private Duration calcularCargaHorariaDoDia(PontoEletronico ponto) {
        var manha = Duration.between(ponto.getEntrada(), ponto.getInicioAlmoco());
        var tarde = Duration.between(ponto.getFimAlmoco(), ponto.getSaida());
        var jornadaTrabalhada = manha.plus(tarde);

        if (jornadaTrabalhada.compareTo(JORNADA_NORMAL) < 0 && ponto.getStatus() == ATESTADO_MATUTINO || ponto.getStatus() == ATESTADO_VESPERTINO) {
            return Duration.ZERO;
        }

        return jornadaTrabalhada;
    }

    private Duration calcularCargaHorariaDeDomingo(PontoEletronico ponto) {
        var horas = calcularCargaHorariaDoDia(ponto);
        return horas.minus(JORNADA_DOMINGO);
    }

    private Duration calcularHorasExtrasSemanais(List<PontoEletronico> pontos) {
        var totalHorasDiaras = pontos.stream()
                .map(this::calcularCargaHorariaDoDia)
                .reduce(Duration.ZERO, Duration::plus);

        return totalHorasDiaras.minus(JORNADA_SEMANAL);
    }

    private Duration calcularHorasExtrasDiarias(List<PontoEletronico> pontos) {
        var horasDeSegundaASabado = calcularHorasExtrasDeSegundaASabado(pontos);
        var horasDeDomingo = calcularHorasExtrasDeDomingo(pontos);

        return horasDeSegundaASabado.plus(horasDeDomingo);
    }

    private Duration calcularHorasExtrasDeSegundaASabado(List<PontoEletronico> pontos) {
        return pontos.stream()
                .filter(ponto -> ponto.getData().getDayOfWeek() != DayOfWeek.SUNDAY & ponto.getStatus() != FERIADO)
                .map(this::calcularCargaHorariaDoDia)
                .map(duration -> duration.minus(JORNADA_NORMAL))
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Duration calcularHorasExtrasDeDomingo(List<PontoEletronico> pontos) {
        return pontos.stream()
                .filter(ponto -> ponto.getData().getDayOfWeek() == DayOfWeek.SUNDAY || ponto.getStatus() == FERIADO)
                .map(this::calcularCargaHorariaDeDomingo)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private List<PontoEletronico> filtrarPontos(List<PontoEletronico> pontos) {
        return pontos.stream()
                .filter(ponto -> !STATUS_REMOVER.contains(ponto.getStatus()))
                .toList();
    }
}
