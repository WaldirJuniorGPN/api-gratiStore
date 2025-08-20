package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.vo.ResultadoPreliminar;
import com.gratiStore.api_gratiStore.domain.utils.StatusUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.*;

@Service
public class CalculadoraDeHorasExtrasImpl implements CalculadoraDeHorasExtras {

    private static final Set<StatusUtils> STATUS_REMOVER = Set.of(ATESTADO_INTEGRAL, FOLGA, FALTA, DESCONTAR_EM_HORAS);
    private static final Duration LIMITE_HORAS_50 = Duration.ofHours(2);
    private static final BigDecimal ADICIONAL_50_POR_CENTO = BigDecimal.valueOf(0.5);
    private static final BigDecimal ADICIONAL_100_POR_CENTO = BigDecimal.valueOf(1);
    private static final BigDecimal SEGUNDO_POR_HORA = BigDecimal.valueOf(3600);
    private static final Duration JORNADA_DOMINGO_OU_FERIADO = Duration.ofHours(6);
    private static final Duration JORNADA_NORMAL = Duration.ofHours(8);
    private static final Duration JORNADA_MEIO_DIA = Duration.ofHours(4);
    private static final Duration JORNADA_SEMANAL = Duration.ofHours(44);
    private static final BigDecimal DIAS_DO_MES = BigDecimal.valueOf(30);
    private static final BigDecimal JORNADA_DIARIA = BigDecimal.valueOf(8);

    @Override
    public Map<Atendente, ResultadoPreliminar> calcularHorasExtras(Map<Integer, List<PontoEletronico>> pontos) {
        var resultado = new HashMap<Atendente, ResultadoPreliminar>();

        pontos.forEach((semana, pontosSemanais) -> {
            Map<Atendente, ResultadoPreliminar> pontosAgrupados = pontosSemanais.stream()
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
                                return descontarHoras(horasASeremDescontadas, resultadoPreliminar, e.getKey().getSalario());
                            }
                    ));

            pontosAgrupados.forEach((atendente, rpSemana) ->
                    resultado.merge(atendente, rpSemana, ResultadoPreliminar::somar));
        });
        return resultado;
    }


    @Override
    public BigDecimal calcularValorAReceber(BigDecimal salario, Duration horasExtras, BigDecimal adicional) {
        var salarioDia = salario.divide(DIAS_DO_MES, MathContext.DECIMAL64);
        var valorHora = salarioDia.divide(JORNADA_DIARIA, MathContext.DECIMAL64);
        var valorHoraExtra = valorHora.add(valorHora.multiply(adicional, MathContext.DECIMAL64));
        var horasDecimais = BigDecimal.valueOf(horasExtras.getSeconds())
                .divide(SEGUNDO_POR_HORA, MathContext.DECIMAL64);
        var totalAReceber = valorHoraExtra.multiply(horasDecimais);

        return totalAReceber.setScale(2, RoundingMode.HALF_UP);
    }

    private ResultadoPreliminar descontarHoras(Duration horasASeremDescontadas, ResultadoPreliminar resultadoPreliminar, BigDecimal salario) {
        var temHoras100Suficiente = resultadoPreliminar.horas100().compareTo(horasASeremDescontadas) > 0;

        if (temHoras100Suficiente) {
            var h100 = resultadoPreliminar.horas100().minus(horasASeremDescontadas);
            var v100 = calcularValorAReceber(salario, h100, ADICIONAL_100_POR_CENTO);

            return new ResultadoPreliminar(resultadoPreliminar.horas50(),
                    h100,
                    resultadoPreliminar.valorAReceber50(),
                    v100);
        }

        var resto = horasASeremDescontadas.minus(resultadoPreliminar.horas100());
        var h50 = resultadoPreliminar.horas50().minus(resto);
        var v50 = calcularValorAReceber(salario, h50, ADICIONAL_50_POR_CENTO);

        return new ResultadoPreliminar(h50, Duration.ZERO, v50, BigDecimal.ZERO);
    }

    private Duration somarHorasASeremDescontadas(List<PontoEletronico> pontos) {
        var quantidadesDePontosADescontar = pontos.stream()
                .filter(ponto -> ponto.getStatus() == DESCONTAR_EM_HORAS)
                .count();

        return JORNADA_NORMAL.multipliedBy(quantidadesDePontosADescontar);
    }

    private ResultadoPreliminar escolherMaior(ResultadoPreliminar diario, ResultadoPreliminar semanal) {
        var valorDiario = diario.valorAReceber50().add(diario.valorAReceber100());
        var valorSemanal = semanal.valorAReceber50();
        return valorDiario.compareTo(valorSemanal) > 0 ? diario : semanal;
    }

    private Duration calcularCargaHorariaDoDia(PontoEletronico ponto) {
        var manha = Duration.between(ponto.getEntrada(), ponto.getInicioAlmoco());
        var tarde = Duration.between(ponto.getFimAlmoco(), ponto.getSaida());
        var jornadaTrabalhada = manha.plus(tarde);

        if (ponto.getStatus() == ATESTADO_MATUTINO) {
            if (ponto.getEntrada().isBefore(LocalTime.NOON)) {
                return jornadaTrabalhada.compareTo(JORNADA_NORMAL) < 0 ? JORNADA_NORMAL : jornadaTrabalhada;
            }
            return jornadaTrabalhada.plus(JORNADA_MEIO_DIA);
        }

        if (ponto.getStatus() == ATESTADO_VESPERTINO) {
            return jornadaTrabalhada.compareTo(JORNADA_NORMAL) < 0 ? JORNADA_NORMAL : jornadaTrabalhada;
        }

        return jornadaTrabalhada;
    }

    private Duration calcularCargaHorariaDeDomingo(PontoEletronico ponto) {
        var horas = calcularCargaHorariaDoDia(ponto);
        return horas.minus(JORNADA_DOMINGO_OU_FERIADO);
    }

    private ResultadoPreliminar calcularHorasExtrasSemanais(List<PontoEletronico> pontos) {
        var totalHorasNaSemana = pontos.stream()
                .map(this::calcularCargaHorariaDoDia)
                .reduce(Duration.ZERO, Duration::plus);
        var horasExtrasDaSemana = totalHorasNaSemana.minus(JORNADA_SEMANAL);
        if (pontos.isEmpty()) {
            return ResultadoPreliminar.vazio();
        }

        return processarResultadoPreliminarDaSemana(pontos.get(0).getAtendente().getSalario(), horasExtrasDaSemana);
    }

    private ResultadoPreliminar calcularHorasExtrasDiarias(List<PontoEletronico> pontos) {
        var r1 = calcularHorasExtrasDeSegundaASabado(pontos);
        var r2 = calcularHorasExtrasDeDomingoOuFeriado(pontos);

        return new ResultadoPreliminar(r1.horas50().plus(r2.horas50()),
                r1.horas100().plus(r2.horas100()),
                r1.valorAReceber50().add(r2.valorAReceber50()),
                r1.valorAReceber100().add(r2.valorAReceber100()));
    }

    private ResultadoPreliminar calcularHorasExtrasDeSegundaASabado(List<PontoEletronico> pontos) {
        return pontos.stream()
                .filter(ponto -> ponto.getData().getDayOfWeek() != DayOfWeek.SUNDAY && ponto.getStatus() != FERIADO)
                .map(ponto -> {
                    var horasExtras = calcularCargaHorariaDoDia(ponto).minus(JORNADA_NORMAL);
                    return processarResultadoPreliminarDiario(ponto.getAtendente().getSalario(), horasExtras);
                })
                .reduce(new ResultadoPreliminar(Duration.ZERO, Duration.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                        (r1, r2) -> new ResultadoPreliminar(
                                r1.horas50().plus(r2.horas50()),
                                r1.horas100().plus(r2.horas100()),
                                r1.valorAReceber50().add(r2.valorAReceber50()),
                                r1.valorAReceber100().add(r2.valorAReceber100())
                        ));
    }

    private ResultadoPreliminar processarResultadoPreliminarDiario(BigDecimal salario, Duration horasExtras) {
        var horas50 = horasExtras.compareTo(LIMITE_HORAS_50) > 0 ? LIMITE_HORAS_50 : horasExtras;
        var horas100 = horasExtras.minus(horas50);

        var valor50 = calcularValorAReceber(salario, horas50, ADICIONAL_50_POR_CENTO);
        var valor100 = calcularValorAReceber(salario, horas100, ADICIONAL_100_POR_CENTO);

        return new ResultadoPreliminar(horas50, horas100, valor50, valor100);
    }

    private ResultadoPreliminar processarResultadoPreliminarDaSemana(BigDecimal salario, Duration horasExtras) {
        var valorAReceber = calcularValorAReceber(salario, horasExtras, ADICIONAL_50_POR_CENTO);

        return new ResultadoPreliminar(horasExtras, Duration.ZERO, valorAReceber, BigDecimal.ZERO);
    }

    private ResultadoPreliminar calcularHorasExtrasDeDomingoOuFeriado(List<PontoEletronico> pontos) {
        return pontos.stream()
                .filter(ponto -> ponto.getData().getDayOfWeek() == DayOfWeek.SUNDAY || ponto.getStatus() == FERIADO)
                .map(ponto -> {
                    var horasExtras = calcularCargaHorariaDeDomingo(ponto);
                    return processarResultadoPreliminarDiario(ponto.getAtendente().getSalario(), horasExtras);
                })
                .reduce(new ResultadoPreliminar(Duration.ZERO, Duration.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                        (r1, r2) -> new ResultadoPreliminar(
                                r1.horas50().plus(r2.horas50()),
                                r1.horas100().plus(r2.horas100()),
                                r1.valorAReceber50().add(r2.valorAReceber50()),
                                r1.valorAReceber100().add(r2.valorAReceber100())
                        ));
    }

    private List<PontoEletronico> filtrarPontos(List<PontoEletronico> pontos) {
        return pontos.stream()
                .filter(ponto -> !STATUS_REMOVER.contains(ponto.getStatus()))
                .toList();
    }
}
