package com.gratiStore.api_gratiStore.domain.service.resultado.impl;

import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponseResultado;
import com.gratiStore.api_gratiStore.domain.service.bonificacao.BonificacaoService;
import com.gratiStore.api_gratiStore.domain.service.gratificacao.GratificacaoService;
import com.gratiStore.api_gratiStore.domain.service.resultado.ResultadoService;
import com.gratiStore.api_gratiStore.domain.service.venda.VendaService;
import com.gratiStore.api_gratiStore.infra.repository.AtendenteRepository;
import com.gratiStore.api_gratiStore.infra.repository.LojaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final LojaRepository lojaRepository;
    private final AtendenteRepository atendenteRepository;
    private final BonificacaoService bonificacaoService;
    private final GratificacaoService gratificacaoService;
    private final VendaService vendaService;

    @Override
    @Transactional
    public List<LojaResponseResultado> calcularResultado() {
        var lojas = lojaRepository.findAllByAtivoTrue();

        for (var loja : lojas) {

            if (loja.getAtendentes().isEmpty()) {
                continue;
            }

            var atendentes = loja.getAtendentes();

            if (loja.getCalculadora() == null) {
                continue;
            }

            var calculadora = loja.getCalculadora();

            gratificacaoService.calcularGratificacaoPrimeiraSemana(atendentes, calculadora);
            gratificacaoService.calcularGratificacaoSegundaSemana(atendentes, calculadora);
            gratificacaoService.calcularGratificacaoTerceiraSemana(atendentes, calculadora);
            gratificacaoService.calcularGratificacaoQuartaSemana(atendentes, calculadora);
            gratificacaoService.calcularGratificacaoQuintaSemana(atendentes, calculadora);
            gratificacaoService.calcularGratificacaoSextaSemana(atendentes, calculadora);

            bonificacaoService.calcularBonusPrimeiraSemana(atendentes, calculadora);
            bonificacaoService.calcularBonusSegundaSemana(atendentes, calculadora);
            bonificacaoService.calcularBonusTerceiraSemana(atendentes, calculadora);
            bonificacaoService.calcularBonusQuartaSemana(atendentes, calculadora);
            bonificacaoService.calcularBonusQuintaSemana(atendentes, calculadora);
            bonificacaoService.calcularBonusSextaSemana(atendentes, calculadora);

            vendaService.calcularVendaTotalPrimeiraSemana(atendentes);
            vendaService.calcularVendaTotalSegundaSemana(atendentes);
            vendaService.calcularVendaTotalTerceiraSemana(atendentes);
            vendaService.calcularVendaTotalQuartaSemana(atendentes);
            vendaService.calcularVendaTotalQuintaSemana(atendentes);
            vendaService.calcularVendaTotalSextaSemana(atendentes);

            vendaService.calcularVendaTotalLoja(loja, atendentes);

            atendenteRepository.saveAll(atendentes);
        }

        lojaRepository.saveAll(lojas);

        return lojas.stream().map(LojaResponseResultado::new).toList();
    }
}
