package com.gratiStore.api_gratiStore.controller;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestVendas;
import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtrasoRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponseVendas;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtrasoResponse;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.planilha.PlanilhaService;
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

    @PostMapping
    public ResponseEntity<AtendenteResponse> criar(@Valid @RequestBody AtendenteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var response = service.criar(request);
        var uri = uriComponentsBuilder.path("/atendentes/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AtendenteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtendenteRequest request) {
        var response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AtendenteResponse>> buscarTodos(Pageable pageable) {
        var response = service.listarTodos(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendenteResponse> buscar(@PathVariable Long id) {
        var response = service.buscar(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AtendenteResponse>> listarTodos() {
        var response = service.listarTodos();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AtendenteResponseVendas> adicionarVendas(@PathVariable Long id, @Valid @RequestBody AtendenteRequestVendas request) {
        var response = service.adicionarVendas(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/upload/primeira-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaPrimeiraSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, PRIMEIRA);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/upload/segunda-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaSegundaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, SEGUNDA);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/upload/terceira-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaTerceiraSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, TERCEIRA);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/upload/quarta-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaQuartaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, QUARTA);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/upload/quinta-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaQuintaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, QUINTA);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/upload/sexta-semana/{lojaId}")
    public ResponseEntity<Void> uploadPlanilhaSextaSemana(@RequestParam("file") MultipartFile file, @PathVariable Long lojaId) throws IOException {
        planilhaService.lerPlanilha(file, lojaId, SEXTA);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/atrasos")
    public ResponseEntity<AtrasoResponse> updateAtrasos(@Valid @RequestBody AtrasoRequest request) {
        var response = service.updateAtraso(request);

        return ResponseEntity.ok(response);
    }
}