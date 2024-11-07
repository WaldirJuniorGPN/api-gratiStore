package com.gratiStore.api_gratiStore.domain.service.resultado.impl;

import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponseResultado;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.resultado.ResultadoService;
import com.gratiStore.api_gratiStore.infra.repository.AtendenteRepository;
import com.gratiStore.api_gratiStore.infra.repository.LojaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final int PRIMEIRO_COLOCADO = 0;
    private final int SEGUNDO_COLOCADO = 1;
    private final int TERCEIRO_COLOCADO = 2;

    private final LojaRepository lojaRepository;
    private final AtendenteRepository atendenteRepository;

    @Override
    @Transactional
    public List<LojaResponseResultado> calcularResultado() {
        var lojas = lojaRepository.findAllByAtivoTrue();

        for (var loja : lojas) {
            var atendentes = loja.getAtendentes();
            var calculadora = loja.getCalculadora();

            calcularGratificacaoPrimeiraSemana(atendentes, calculadora);
            calcularGratificacaoSegundaSemana(atendentes, calculadora);
            calcularGratificacaoTerceiraSemana(atendentes, calculadora);
            calcularGratificacaoQuartaSemana(atendentes, calculadora);
            calcularGratificacaoQuintaSemana(atendentes, calculadora);
            calcularGratificacaoSextaSemana(atendentes, calculadora);

            calcularBonusPrimeiraSemana(atendentes, calculadora);
            calcularBonusSegundaSemana(atendentes, calculadora);
            calcularBonusTerceiraSemana(atendentes, calculadora);
            calcularBonusQuartaSemana(atendentes, calculadora);
            calcularBonusQuintaSemana(atendentes, calculadora);
            calcularBonusSextaSemana(atendentes, calculadora);

            calcularVendaTotalPrimeiraSemana(atendentes);
            calcularVendaTotalSegundaSemana(atendentes);
            calcularVendaTotalTerceiraSemana(atendentes);
            calcularVendaTotalQuartaSemana(atendentes);
            calcularVendaTotalQuintaSemana(atendentes);
            calcularVendaTotalSextaSemana(atendentes);

            calcularVendaTotalLoja(loja, atendentes);

            atendenteRepository.saveAll(atendentes);
        }

        lojaRepository.saveAll(lojas);

        return lojas.stream().map(LojaResponseResultado::new).toList();
    }

    private void calcularGratificacaoPrimeiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        zerarGratificacao(atendentes);
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasPrimeiraSemana);
    }

    private void calcularGratificacaoSegundaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasSegundaSemana);
    }

    private void calcularGratificacaoTerceiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasTerceiraSemana);
    }

    private void calcularGratificacaoQuartaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasQuartaSemana);
    }

    private void calcularGratificacaoQuintaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasQuintaSemana);
    }

    private void calcularGratificacaoSextaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularGratificacaoSemanal(atendentes, calculadora, Atendente::getVendasSextaSemana);
    }

    private void calcularBonusPrimeiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        zerarBonus(atendentes);
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosPrimeiraSemana, Atendente::getAtrasoStatusPrimeiraSemana);
    }

    private void calcularBonusSegundaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosSegundaSemana, Atendente::getAtrasoStatusSegundaSemana);
    }

    private void calcularBonusTerceiraSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosTerceiraSemana, Atendente::getAtrasoStatusTerceiraSemana);
    }

    private void calcularBonusQuartaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosQuartaSemana, Atendente::getAtrasoStatusQuartaSemana);
    }

    private void calcularBonusQuintaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosQuintaSemana, Atendente::getAtrasoStatusQuintaSemana);
    }

    private void calcularBonusSextaSemana(List<Atendente> atendentes, Calculadora calculadora) {
        calcularBonificacaoSemanal(atendentes, calculadora, Atendente::getQuantidadeAtendimentosSextaSemana, Atendente::getAtrasoStatusSextaSemana);
    }

    private void calcularVendaTotalPrimeiraSemana(List<Atendente> atendentes) {
        zerarVendaTotal(atendentes);
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasPrimeiraSemana);
    }

    private void calcularVendaTotalSegundaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasSegundaSemana);
    }

    private void calcularVendaTotalTerceiraSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasTerceiraSemana);
    }

    private void calcularVendaTotalQuartaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasQuartaSemana);
    }

    private void calcularVendaTotalQuintaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasQuintaSemana);
    }

    private void calcularVendaTotalSextaSemana(List<Atendente> atendentes) {
        calcularVendaTotalSemanal(atendentes, Atendente::getVendasSextaSemana);
    }

    private void zerarGratificacao(List<Atendente> atendentes) {
        atendentes.forEach(atendente -> atendente.setGratificacao(BigDecimal.ZERO));
    }

    private void zerarBonus(List<Atendente> atendentes) {
        atendentes.forEach(atendente -> atendente.setBonus(BigDecimal.ZERO));
    }

    private void zerarVendaTotal(List<Atendente> atendentes) {
        atendentes.forEach(atendente -> atendente.setTotalVendas(BigDecimal.ZERO));
    }

    private void zerarVendaTotalLoja(Loja loja) {
        loja.setTotalVendas(BigDecimal.ZERO);
    }

    private void calcularVendaTotalLoja(Loja loja, List<Atendente> atendentes) {
        zerarVendaTotalLoja(loja);

        var totalVendas = atendentes.stream()
                .map(Atendente::getTotalVendas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        loja.atribuirVendas(totalVendas);
    }

    private void calcularVendaTotalSemanal(List<Atendente> atendentes, Function<Atendente, BigDecimal> vendasSemana) {
        atendentes.forEach(atendente -> atendente.atribuirVendaTotal(vendasSemana.apply(atendente)));
    }

    private void calcularGratificacaoSemanal(List<Atendente> atendentes, Calculadora calculadora, Function<Atendente, BigDecimal> vendasSemana) {
        var atendentesOrdenados = atendentes.stream()
                .sorted(Comparator.comparing(vendasSemana).reversed())
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

    private void calcularBonificacaoSemanal(List<Atendente> atendentes, Calculadora calculadora, Function<Atendente, Integer> atendimentosSemana, Function<Atendente, AtrasoStatus> status) {
        var atendentesOrdenados = atendentes.stream()
                .sorted(Comparator.comparing(atendimentosSemana).reversed())
                .toList();

        for (int i = 0; i < atendentesOrdenados.size(); i++) {
            if (status.apply(atendentesOrdenados.get(i)).equals(AtrasoStatus.NAO)) {
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
}
