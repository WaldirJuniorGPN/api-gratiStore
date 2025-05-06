package com.gratiStore.api_gratiStore.domain.service.horasExtras;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;

import java.time.Duration;
import java.util.List;

public interface CalculadoraDeHorasExtras {

    Duration calcular(List<PontoEletronico> pontos);
}
