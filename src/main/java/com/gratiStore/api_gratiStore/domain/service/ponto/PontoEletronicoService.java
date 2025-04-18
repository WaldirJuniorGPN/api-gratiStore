package com.gratiStore.api_gratiStore.domain.service.ponto;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PontoEletronicoService {

    void registrarPronto(PontoRequest request);

    Page<HistoricoResponse> listarHistorico(Pageable pageable);
}
