package com.gratiStore.api_gratiStore.infra.adapter.horasExtras;

import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;

public interface HorasExtrasAdapter {

    ResultadoHorasExtrasResponse horasExtrasToResultadoHorasExtrasResponse(ResultadoHoraExtra horasExtras);
}
