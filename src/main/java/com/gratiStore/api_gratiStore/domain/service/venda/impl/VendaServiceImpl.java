package com.gratiStore.api_gratiStore.domain.service.venda.impl;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.venda.VendaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Service
public class VendaServiceImpl implements VendaService {

    @Override
    public void calcularVendaTotalPrimeiraSemana(List<Atendente> atendentes) {
        zerarVendaTotal(atendentes);
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasPrimeiraSemana);
    }

    @Override
    public void calcularVendaTotalSegundaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasSegundaSemana);
    }

    @Override
    public void calcularVendaTotalTerceiraSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasTerceiraSemana);
    }

    @Override
    public void calcularVendaTotalQuartaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasQuartaSemana);
    }

    @Override
    public void calcularVendaTotalQuintaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasQuintaSemana);
    }

    @Override
    public void calcularVendaTotalSextaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasSextaSemana);
    }

    @Override
    public void calcularVendaTotalLoja(Loja loja, List<Atendente> atendentes) {
        zerarVendaTotalLoja(loja);

        var totalVendas = atendentes.stream()
                .map(Atendente::getTotalVendas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        loja.atribuirVendas(totalVendas);
    }

    private void calcularVendaTotalSemanal(List<Atendente> atendentes, Function<Atendente, BigDecimal> vendasSemana) {
        atendentes.forEach(atendente -> atendente.atribuirVendaTotal(vendasSemana.apply(atendente)));
    }

    private void zerarVendaTotal(List<Atendente> atendentes) {
        atendentes.forEach(atendente -> atendente.setTotalVendas(BigDecimal.ZERO));
    }

    private void zerarVendaTotalLoja(Loja loja) {
        loja.setTotalVendas(BigDecimal.ZERO);
    }
}
