package com.gratiStore.api_gratiStore.infra.port;

import com.gratiStore.api_gratiStore.domain.service.planilha.impl.PlanilhaMessage;

public interface PlanilhaPublisher {
    void publicar(PlanilhaMessage evento);
}
