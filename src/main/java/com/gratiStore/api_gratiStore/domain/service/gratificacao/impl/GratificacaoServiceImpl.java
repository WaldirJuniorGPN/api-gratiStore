package com.gratiStore.api_gratiStore.domain.service.gratificacao.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.service.gratificacao.GratificacaoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;


@Service
public class GratificacaoServiceImpl implements GratificacaoService {

    private final int PRIMEIRO_COLOCADO = 0;
    private final int SEGUNDO_COLOCADO = 1;
    private final int TERCEIRO_COLOCADO = 2;

    @Override
    public void calcularGratificacaoPrimeiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        zerarGratificacao(atendentes);
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasPrimeiraSemana);
    }

    @Override
    public void calcularGratificacaoSegundaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasSegundaSemana);
    }

    @Override
    public void calcularGratificacaoTerceiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasTerceiraSemana);
    }

    @Override
    public void calcularGratificacaoQuartaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasQuartaSemana);
    }

    @Override
    public void calcularGratificacaoQuintaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasQuintaSemana);
    }

    @Override
    public void calcularGratificacaoSextaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasSextaSemana);
    }

    private void zerarGratificacao(List<Atendente> atendentes) {
        atendentes.forEach(atendente -> atendente.setGratificacao(BigDecimal.ZERO));
    }

    private void calcularGratificacaoSemanal(List<Atendente> atendentes, Calculadora calculadora, Function<Atendente, BigDecimal> vendasSemana) {
        var atendentesOrdenados = atendentes.stream()
                .sorted(Comparator.comparing(vendasSemana, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();

        for (int i = 0; i < atendentesOrdenados.size(); i++) {
            BigDecimal percentual;
            switch (i) {
                case PRIMEIRO_COLOCADO -> percentual = BigDecimal.valueOf(calculadora.getPercentualPrimeiroColocado());
                case SEGUNDO_COLOCADO -> percentual = BigDecimal.valueOf(calculadora.getPercentualSegundoColocado());
                case TERCEIRO_COLOCADO -> percentual = BigDecimal.valueOf(calculadora.getPercentualTerceiroColocado());
                default -> percentual = BigDecimal.valueOf(calculadora.getPercentualDemaisColocados());
            }
            atendentesOrdenados.get(i).atribuirGratificacao(vendasSemana.apply(atendentesOrdenados.get(i)).multiply(percentual));
        }
    }
}
