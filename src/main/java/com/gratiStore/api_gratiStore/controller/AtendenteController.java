package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestVendas;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtrasoRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.UpdateSalarioRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.*;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.planilha.PlanilhaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.utils.SemanaUtils.*;

@RestController
@RequestMapping("/atendentes")
@RequiredArgsConstructor
public class AtendenteController {

    private final AtendenteService service;
    private final PlanilhaService planilhaService;

    @Operation(
            summary = "Cadastrar novo Atendente",
            description = "Registra um novo atendente com nome e ID da loja associada. O atendente será ativado automaticamente após o cadastro."
    )
    @PostMapping
    public ResponseEntity<AtendenteResponse> criar(@Valid @RequestBody AtendenteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/atendentes/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(
            summary = "Atualizar Atendente existente",
            description = "Atualiza os dados de um atendente ativo com base no ID informado e nos campos fornecidos na requisição."
    )
    @PutMapping("/{id}")
    public ResponseEntity<AtendenteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtendenteRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar Atendentes ativos (paginado)",
            description = "Retorna uma lista paginada contendo os atendentes com status ativo. Cada item inclui ID, nome e loja associada."
    )
    @GetMapping
    public ResponseEntity<Page<AtendenteResponse>> buscarTodos(Pageable pageable) {
        var response = service.listarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar Atendente por ID",
            description = "Retorna os dados de um atendente ativo com base no ID informado. Inclui nome e loja associada."
    )
    @GetMapping("/{id}")
    public ResponseEntity<AtendenteResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar salário do Atendente",
            description = "Retorna o salário do Atendente ativo com base no ID informado. Inclui nome e salário associado."
    )
    @GetMapping("/salario/{id}")
    public ResponseEntity<SalarioAtendenteResponse> buscarSalario(@PathVariable Long id) {
        var response = service.buscarSalarioAtendente(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar todos os Atendentes ativos (não paginado)",
            description = "Retorna todos os atendentes ativos em formato de lista simples, com seus respectivos IDs, nomes e lojas associadas."
    )
    @GetMapping("/listar")
    public ResponseEntity<List<AtendenteResponse>> listarTodos() {
        var response = service.listarTodos();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Desativar Atendente",
            description = "Realiza a deleção lógica do atendente, alterando seu status para inativo. O nome será marcado como 'Excluído' com data/hora."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Registrar vendas e atendimentos do Atendente",
            description = "Registra os valores de vendas e quantidade de atendimentos por semana (de 1 a 6) para o atendente correspondente."
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AtendenteResponseVendas> adicionarVendas(@PathVariable Long id, @Valid @RequestBody AtendenteRequestVendas request) {
        var response = service.adicionarVendas(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Upload de planilha da primeira semana",
            description = "Processa o arquivo Excel (.xlsx) com dados de vendas e atendimentos da [semana]. Se o atendente não existir, será criado automaticamente e vinculado à loja especificada."
    )
    @PatchMapping("/upload/primeira-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaPrimeiraSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, PRIMEIRA);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Upload de planilha da segunda semana",
            description = "Processa o arquivo Excel (.xlsx) com dados de vendas e atendimentos da [semana]. Se o atendente não existir, será criado automaticamente e vinculado à loja especificada."
    )
    @PatchMapping("/upload/segunda-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaSegundaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, SEGUNDA);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Upload de planilha da terceira semana",
            description = "Processa o arquivo Excel (.xlsx) com dados de vendas e atendimentos da [semana]. Se o atendente não existir, será criado automaticamente e vinculado à loja especificada."
    )
    @PatchMapping("/upload/terceira-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaTerceiraSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, TERCEIRA);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Upload de planilha da quarta semana",
            description = "Processa o arquivo Excel (.xlsx) com dados de vendas e atendimentos da [semana]. Se o atendente não existir, será criado automaticamente e vinculado à loja especificada."
    )
    @PatchMapping("/upload/quarta-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaQuartaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, QUARTA);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Upload de planilha da quinta semana",
            description = "Processa o arquivo Excel (.xlsx) com dados de vendas e atendimentos da [semana]. Se o atendente não existir, será criado automaticamente e vinculado à loja especificada."
    )
    @PatchMapping("/upload/quinta-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaQuintaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, QUINTA);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Upload de planilha da sexta semana",
            description = "Processa o arquivo Excel (.xlsx) com dados de vendas e atendimentos da [semana]. Se o atendente não existir, será criado automaticamente e vinculado à loja especificada."
    )
    @PatchMapping("/upload/sexta-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaSextaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, SEXTA);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualizar status de atraso do Atendente",
            description = "Registra se o atendente teve ou não atraso em uma semana específica."
    )
    @PatchMapping("/update/atrasos")
    public ResponseEntity<AtrasoResponse> updateAtrasos(@Valid @RequestBody AtrasoRequest request) {
        var response = service.updateAtraso(request);

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Atualiza o valor do salário do Atendente",
        description = "Registra na base o salário de um atendente específico."
    )
    @PatchMapping("/update/salario")
    public ResponseEntity<UpdateSalarioResponse> updateSalario(@Valid @RequestBody UpdateSalarioRequest request) {
        var response = service.atualizarSalario(request);

        return ResponseEntity.ok(response);
    }
}