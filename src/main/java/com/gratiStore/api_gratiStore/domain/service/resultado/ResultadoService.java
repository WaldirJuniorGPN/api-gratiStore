package com.gratiStore.api_gratiStore.domain.service.resultado;

import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponseResultado;

import java.util.List;

public interface ResultadoService {

    List<LojaResponseResultado> calcularResultado();
}
