package com.gratiStore.api_gratiStore.domain.service.horasExtras;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResumoHorasExtrasResponse;

import java.util.List;

public interface HorasExtrasService {

    List<ResumoHorasExtrasResponse> calcular(FiltroHorasExtrasRequest request);
}
