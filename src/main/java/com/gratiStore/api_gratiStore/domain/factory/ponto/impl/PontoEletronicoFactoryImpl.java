package com.gratiStore.api_gratiStore.domain.factory.ponto.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.factory.ponto.PontoEletronicoFactory;
import org.springframework.stereotype.Component;

@Component
public class PontoEletronicoFactoryImpl implements PontoEletronicoFactory {

    @Override
    public PontoEletronico criar(PontoRequest request, Atendente atendente) {
        return new PontoEletronico(request.data(), request.entrada(), request.inicioAlmoco(), request.fimAlmoco(),
                request.saida(), atendente);
    }
}
