package com.gratiStore.api_gratiStore.domain.service.bonificacao.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.service.bonificacao.BonificacaoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class BonificacaoServiceImpl implements BonificacaoService {

    private final int PRIMEIRO_COLOCADO = 0;
    private final int SEGUNDO_COLOCADO = 1;
    private final int TERCEIRO_COLOCADO = 2;


    @Override
    public void calcularBonusPrimeiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        zerarBonus(atendentes);
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosPrimeiraSemana, Atendente::getAtrasoStatusPrimeiraSemana);
    }

    @Override
    public void calcularBonusSegundaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosSegundaSemana, Atendente::getAtrasoStatusSegundaSemana);
    }

    @Override
    public void calcularBonusTerceiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosTerceiraSemana, Atendente::getAtrasoStatusTerceiraSemana);
    }

    @Override
    public void calcularBonusQuartaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosQuartaSemana, Atendente::getAtrasoStatusQuartaSemana);
    }

    @Override
    public void calcularBonusQuintaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosQuintaSemana, Atendente::getAtrasoStatusQuintaSemana);
    }

    @Override
    public void calcularBonusSextaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosSextaSemana, Atendente::getAtrasoStatusSextaSemana);
    }

    private void calcularBonificacaoSemanal(List<Atendente> atendentes, Calculadora calculadora, Function<Atendente, Integer> atendimentosSemana, Function<Atendente, AtrasoStatus> status) {
        var atendentesOrdenados = atendentes.stream()
                .sorted(Comparator.comparing(atendimentosSemana, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();

        for (int i = 0; i < atendentesOrdenados.size(); i++) {
            if (AtrasoStatus.NAO.equals(status.apply(atendentesOrdenados.get(i)))) {
                BigDecimal bonus;
                switch (i) {
                    case PRIMEIRO_COLOCADO -> bonus = calculadora.getBonusPrimeiroColocado();
                    case SEGUNDO_COLOCADO -> bonus = calculadora.getBonusSegundoColocado();
                    case TERCEIRO_COLOCADO -> bonus = calculadora.getBonusTerceiroColocado();
                    default -> bonus = BigDecimal.ZERO;
                }
                if (atendimentosSemana.apply(atendentesOrdenados.get(i)) > 0) {
                    atendentesOrdenados.get(i).atribuirBonus(bonus);
                }
            }
        }
    }

    private void zerarBonus(List<Atendente> atendentes) {
        atendentes.forEach(atendente -> atendente.setBonus(BigDecimal.ZERO));
    }
}
