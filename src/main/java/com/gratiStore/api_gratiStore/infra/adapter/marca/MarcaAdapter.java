package com.gratiStore.api_gratiStore.infra.adapter.marca;

import com.gratiStore.api_gratiStore.controller.dto.request.marca.MarcaRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.marca.MarcaResponse;
import com.gratiStore.api_gratiStore.domain.entities.marca.Marca;

public interface MarcaAdapter {

    Marca marcaRequestToMarca(MarcaRequest request);

    MarcaResponse marcaToMarcaResponse(Marca marca);

    void marcaRequestToMarca(Marca marca, MarcaRequest request);
}
