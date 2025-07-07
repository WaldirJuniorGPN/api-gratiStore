package com.gratiStore.api_gratiStore.domain.service.horasExtras;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface HorasExtrasService {

    void calcular(FiltroHorasExtrasRequest request);

    List<ResultadoHorasExtrasResponse> buscar(@Valid FiltroHorasExtrasRequest request);
}
