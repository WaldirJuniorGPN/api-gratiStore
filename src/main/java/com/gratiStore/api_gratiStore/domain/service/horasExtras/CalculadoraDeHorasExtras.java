package com.gratiStore.api_gratiStore.domain.service.horasExtras;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface CalculadoraDeHorasExtras {

    Map<Atendente, Duration> calcularHorasExtras(Map<Integer, List<PontoEletronico>> pontos);

    BigDecimal calcularValorAReceber(BigDecimal salario, Duration horasExtras, BigDecimal adicional);
}
