package com.gratiStore.api_gratiStore.domain.service.planilha.impl;

import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;

import java.io.Serializable;

public record PlanilhaMessage(
        byte[] conteudo,
        String nomeArquivo,
        String contentType,
        Long lojaId,
        SemanaUtils semana) implements Serializable {
}
