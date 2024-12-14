package com.gratiStore.api_gratiStore.infra.adapter.marca.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.marca.MarcaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.marca.MarcaResponse;
import com.gratiStore.api_gratiStore.domain.entities.marca.Marca;
import com.gratiStore.api_gratiStore.infra.adapter.marca.MarcaAdapter;
import org.springframework.stereotype.Component;

@Component
public class MarcaAdapterImpl implements MarcaAdapter {

    @Override
    public Marca marcaRequestToMarca(MarcaRequest request) {
        var marca = new Marca();
        marca.setNome(request.nome());

        return marca;
    }

    @Override
    public MarcaResponse marcaToMarcaResponse(Marca marca) {
        return new MarcaResponse(marca);
    }

    @Override
    public void marcaRequestToMarca(Marca marca, MarcaRequest request) {
        marca.setNome(request.nome());
    }
}
