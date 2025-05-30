package com.gratiStore.api_gratiStore.domain.service.bonificacao;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;

import java.util.List;
import java.util.function.Function;

public interface BonificacaoService {

    void calcularBonusPrimeiraSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularBonusSegundaSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularBonusTerceiraSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularBonusQuartaSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularBonusQuintaSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularBonusSextaSemana(List<Atendente> atendentes, Calculadora calculadora);
}
