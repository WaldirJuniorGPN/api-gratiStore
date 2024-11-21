package com.gratiStore.api_gratiStore.domain.service.planilha;

import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PlanilhaService {

    void lerPlanilha(MultipartFile file, Long lojaId, SemanaUtils semana) throws IOException;
}
