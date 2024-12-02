package com.gratiStore.api_gratiStore.domain.service.loja;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LojaService {

    LojaResponse criar(LojaRequest request);

    LojaResponse atualizar(Long id, LojaRequest request);

    LojaResponse buscar(Long id);

    LojaResponse buscar(String cnpj);

    Page<LojaResponse> listarTodos(Pageable pageable);

    List<LojaResponse> lsitarTodos();

    void deletar(Long id);

    Loja buscarLoja(Long id);

    List<AtendenteResponse> listarAtendentesPorLoja(Long id);

    void salvarNoBanco(Loja loja);

    void zerarValoresAtendentes(Long lojaId);
}
