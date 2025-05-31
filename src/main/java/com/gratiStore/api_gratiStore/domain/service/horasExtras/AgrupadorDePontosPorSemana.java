package com.gratiStore.api_gratiStore.domain.service.horasExtras;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;

import java.util.List;
import java.util.Map;

public interface AgrupadorDePontosPorSemana {

    Map<Integer, List<PontoEletronico>> agrupar(List<PontoEletronico> pontos);
}
