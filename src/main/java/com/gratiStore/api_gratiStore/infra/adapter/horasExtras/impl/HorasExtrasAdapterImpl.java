package com.gratiStore.api_gratiStore.infra.adapter.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;
import com.gratiStore.api_gratiStore.infra.adapter.horasExtras.HorasExtrasAdapter;
import org.springframework.stereotype.Component;

@Component
public class HorasExtrasAdapterImpl implements HorasExtrasAdapter {

    @Override
    public ResultadoHorasExtrasResponse horasExtrasToResultadoHorasExtrasResponse(ResultadoHoraExtra horasExtras) {

        return new ResultadoHorasExtrasResponse(horasExtras.getAtendente().getNome(),
                horasExtras.getMes(),
                horasExtras.getAno(),
                horasExtras.getValorAReceber50PorCento(),
                horasExtras.getValorAReceber100PorCento(),
                horasExtras.getHorasExtras());
    }
}
