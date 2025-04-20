package com.gratiStore.api_gratiStore.domain.service.gratificacao;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;

import java.util.List;

public interface GratificacaoService {

    void calcularGratificacaoPrimeiraSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularGratificacaoSegundaSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularGratificacaoTerceiraSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularGratificacaoQuartaSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularGratificacaoQuintaSemana(List<Atendente> atendentes, Calculadora calculadora);

    void calcularGratificacaoSextaSemana(List<Atendente> atendentes, Calculadora calculadora);
}
