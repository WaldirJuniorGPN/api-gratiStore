package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.loja.LojaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.LojaResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.loja.VendasResponse;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/lojas")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService service;

    @Operation(
            summary = "Cadastrar nova Loja",
            description = "Registra uma nova loja com os dados fornecidos na requisição."
    )
    @PostMapping
    public ResponseEntity<LojaResponse> criar(@Valid @RequestBody LojaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/lojas/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(
            summary = "Atualizar Loja existente",
            description = "Atualiza os dados de uma loja ativa com base no ID informado."
    )
    @PutMapping("/{id}")
    public ResponseEntity<LojaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody LojaRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar Loja por ID",
            description = "Retorna os dados de uma loja ativa com base no ID informado."
    )
    @GetMapping("/{id}")
    public ResponseEntity<LojaResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar Loja por CNPJ",
            description = "Retorna os dados de uma loja ativa com base no CNPJ informado."
    )
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<LojaResponse> buscar(@PathVariable String cnpj) {
        var response = service.buscar(cnpj);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar Lojas ativas (paginado)",
            description = "Retorna uma lista paginada contendo as lojas com status ativo."
    )
    @GetMapping
    public ResponseEntity<Page<LojaResponse>> listarTodos(Pageable pageable) {
        var response = service.listarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar todas as Lojas ativas (não paginado)",
            description = "Retorna todas as lojas ativas em formato de lista simples."
    )
    @GetMapping("/listar")
    public ResponseEntity<List<LojaResponse>> listarTodos() {
        var response = service.lsitarTodos();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar Atendentes de uma Loja",
            description = "Retorna uma lista de atendentes associados a uma loja específica com base no ID informado."
    )
    @GetMapping("/{id}/atendentes")
    public ResponseEntity<List<AtendenteResponse>> listarAtendentesPorLoja(@PathVariable Long id) {
        var response = service.listarAtendentesPorLoja(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar Vendas Totais de uma Loja",
            description = "Retorna o total de vendas de uma loja específica com base no ID informado."
    )
    @GetMapping("/{id}/vendas")
    public ResponseEntity<VendasResponse> buscaVendasTotais(@PathVariable Long id) {
        var response = service.buscarVendasTotais(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Desativar Loja",
            description = "Realiza a deleção lógica da loja, alterando seu status para inativo."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Zerar Valores dos Atendentes de uma Loja",
            description = "Zera os valores de vendas, atendimentos e bonificações de todos os atendentes associados a uma loja específica."
    )
    @PatchMapping("/{lojaId}")
    public ResponseEntity<Void> zerarValoresAtendentes(@PathVariable Long lojaId) {
        service.zerarValoresAtendentes(lojaId);

        return ResponseEntity.noContent().build();
    }
}
