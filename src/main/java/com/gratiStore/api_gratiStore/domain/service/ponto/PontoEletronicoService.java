package com.gratiStore.api_gratiStore.domain.service.ponto;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.ponto.HistoricoResponse;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PontoEletronicoService {

    void registrarPonto(PontoRequest request);

    Page<HistoricoResponse> listarHistorico(Pageable pageable);

    List<PontoEletronico> listarHistorico(FiltroHorasExtrasRequest request);

    HistoricoResponse atualizar(Long id, PontoRequest request);

    void deletar(Long id);

    HistoricoResponse buscar(Long id);
}
