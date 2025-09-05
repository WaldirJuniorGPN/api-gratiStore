package com.gratiStore.api_gratiStore.infra.port;

import com.gratiStore.api_gratiStore.domain.service.planilha.impl.LeitorDePlanilhaVO;

public interface PlanilhaPublisher {
    void publicar(LeitorDePlanilhaVO evento);
}
