package com.gratiStore.api_gratiStore.domain.service.atendente;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.*;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.*;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AtendenteService {

    AtendenteResponse criar(AtendenteRequest request);

    void uploadSemana(AtendenteRequestPlanilha request, SemanaUtils semana);

    AtendenteResponse atualizar(Long id, AtendenteRequest request);

    AtendenteResponse buscar(Long id);

    Atendente buscarNoBanco(Long id);

    Page<AtendenteResponse> listarTodos(Pageable pageable);

    List<AtendenteResponse> listarTodos();

    void deletar(Long id);

    AtendenteResponseVendas adicionarVendas(Long id, AtendenteRequestVendas request);

    AtrasoResponse updateAtraso(AtrasoRequest request);

    UpdateSalarioResponse atualizarSalario(UpdateSalarioRequest request);

    SalarioAtendenteResponse buscarSalarioAtendente(Long id);
}
