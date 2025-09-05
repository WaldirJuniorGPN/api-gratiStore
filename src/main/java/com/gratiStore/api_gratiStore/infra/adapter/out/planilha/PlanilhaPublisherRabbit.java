package com.gratiStore.api_gratiStore.infra.adapter.out.planilha;

import com.gratiStore.api_gratiStore.domain.service.planilha.impl.LeitorDePlanilhaVO;
import com.gratiStore.api_gratiStore.infra.config.planilha.LerPlanilhaMensagemTopologyConfig;
import com.gratiStore.api_gratiStore.infra.port.PlanilhaPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlanilhaPublisherRabbit implements PlanilhaPublisher {

    private final RabbitTemplate template;

    public PlanilhaPublisherRabbit(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void publicar(LeitorDePlanilhaVO evento) {
        template.convertAndSend(LerPlanilhaMensagemTopologyConfig.EXCHANGE, LerPlanilhaMensagemTopologyConfig.ROUTING_KEY, evento);
    }
}
