package com.gratiStore.api_gratiStore.domain.service.horasExtras;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface CalculadoraDeHorasExtras {

    Duration calcular(Map<Integer, List<PontoEletronico>> pontos);
}
