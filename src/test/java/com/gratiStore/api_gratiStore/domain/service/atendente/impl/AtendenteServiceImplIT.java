package com.gratiStore.api_gratiStore.domain.service.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.infra.config.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AtendenteServiceImplIT extends TestContainerConfig {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private AtendenteService atendenteService;

    @BeforeEach
    void setUp() {
        var loja = new Loja("Americanas", "06026378000140");
        lojaService.salvarNoBanco(loja);
    }

    @Test
    void deveCriarAtendenteComSucesso() {
        var request = new AtendenteRequest("Fulano", 1L, BigDecimal.valueOf(3000));
        var response = atendenteService.criar(request);

        assertEquals(response.nome(), request.nome());
        assertNotNull(response.id());
    }
}
