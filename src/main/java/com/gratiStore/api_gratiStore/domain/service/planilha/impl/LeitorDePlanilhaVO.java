package com.gratiStore.api_gratiStore.domain.service.planilha.impl;

import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record LeitorDePlanilhaVO(
        @NotNull
        MultipartFile file,

        @NotNull
        Long lojaId,

        @NotNull
        SemanaUtils semana) {
}
