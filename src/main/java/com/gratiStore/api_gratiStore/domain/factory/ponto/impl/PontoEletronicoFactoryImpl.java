package com.gratiStore.api_gratiStore.domain.factory.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.factory.ponto.PontoEletronicoFactory;

public class PontoEletronicoFactoryImpl implements PontoEletronicoFactory {

    @Override
    public PontoEletronico criar(PontoRequest request, Atendente atendente) {
        validar(request, atendente);

        return new PontoEletronico(request.data(), request.entrada(), request.inicioAlmoco(), request.fimAlmoco(),
                request.saida(), atendente);
    }

    private void validar(PontoRequest request, Atendente atendente) {
        if (request.data() == null) {
            throw new IllegalStateException("A data não pode estar nula");
        }
        if (request.entrada() == null) {
            throw new IllegalStateException("A hora da entrada não pode estar nula");
        }
        if (request.inicioAlmoco() == null) {
            throw new IllegalStateException("A hora de início do almoço não pode estar nula");
        }

        if (request.fimAlmoco() == null) {
            throw new IllegalStateException("A hora do fim do almoço não pode estar nula");
        }
        if (request.saida() == null) {
            throw new IllegalStateException("A hora de saída não pode ser nula");
        }

        if (request.inicioAlmoco().isBefore(request.entrada())) {
            throw new IllegalStateException("A hora de início do almoço não pode ser anterior à hora de entrada");
        }
        if (request.fimAlmoco().isBefore(request.inicioAlmoco())) {
            throw new IllegalStateException("A hora do fim do almoço não pode ser anterior a ao início do almoço");
        }
        if (request.saida().isBefore(request.fimAlmoco())) {
            throw new IllegalStateException("A hora de saída não pode ser anteior ao fim do almoço");
        }
    }
}
