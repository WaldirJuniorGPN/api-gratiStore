package com.gratiStore.api_gratiStore.domain.service.venda;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;

import java.util.List;

public interface VendaService {

    void calcularVendaTotalPrimeiraSemana(List<Atendente> atendentes);

    void calcularVendaTotalSegundaSemana(List<Atendente> atendentes);

    void calcularVendaTotalTerceiraSemana(List<Atendente> atendentes);

    void calcularVendaTotalQuartaSemana(List<Atendente> atendentes);

    void calcularVendaTotalQuintaSemana(List<Atendente> atendentes);

    void calcularVendaTotalSextaSemana(List<Atendente> atendentes);

    void calcularVendaTotalLoja(Loja loja, List<Atendente> atendentes);
}
