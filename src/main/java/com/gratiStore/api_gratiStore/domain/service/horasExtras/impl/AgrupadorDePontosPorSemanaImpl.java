package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AgrupadorDePontosPorSemanaImpl implements AgrupadorDePontosPorSemana {

    @Override
    public Map<Integer, List<PontoEletronico>> agrupar(List<PontoEletronico> pontos) {
        var weekFilds = WeekFields.of(DayOfWeek.SUNDAY, 1);

        return pontos.stream()
                .collect(Collectors.groupingBy(ponto ->
                        ponto.getData().get(weekFilds.weekOfMonth())));
    }
}
