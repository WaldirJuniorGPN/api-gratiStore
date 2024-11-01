package com.gratiStore.api_gratiStore.infra.adapter.atendente.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.atendente.AtendenteResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.infra.adapter.atendente.AtendenteAdapter;
import com.gratiStore.api_gratiStore.infra.repository.LojaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtendenteAdapterImpl implements AtendenteAdapter {

    private final LojaRepository lojaRepository;

    @Override
    public Atendente atendenteRequestToAtendente(AtendenteRequest request) {
        var atendente = new Atendente();
        atendente.setNome(request.nome());
        atendente.setLoja(buscarLojaNoBanco(request.lojaId()));

        return atendente;
    }

    @Override
    public AtendenteResponse atendenteToAtendenteResponse(Atendente atendente) {
        return new AtendenteResponse(atendente);
    }

    @Override
    public Atendente atendenteReqquestToAtendente(Atendente atendente, AtendenteRequest request) {
        atendente.setNome(request.nome());
        atendente.setLoja(buscarLojaNoBanco(request.lojaId()));

        return atendente;
    }

    private Loja buscarLojaNoBanco(Long id) {
        return lojaRepository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Loja com ID: %d não encontrada ou não está ativa", id)));
    }
}
