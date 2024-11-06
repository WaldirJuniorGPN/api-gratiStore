package com.gratiStore.api_gratiStore.controller.dto.response.loja;

import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponseResultado;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record LojaResponseResultado(Long id, String nome, List<AtendenteResponseResultado> atendentes,
                                    BigDecimal totalVendas) {

    public LojaResponseResultado(Loja loja) {
        this(loja.getId(), loja.getNome(), loja.getAtendentes().stream()
                .map(AtendenteResponseResultado::new)
                .collect(Collectors.toList()), loja.getTotalVendas());
    }
}
