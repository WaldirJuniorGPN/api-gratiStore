package com.gratiStore.api_gratiStore.infra.adapter.in.planilha;

import com.gratiStore.api_gratiStore.domain.service.planilha.PlanilhaService;
import com.gratiStore.api_gratiStore.domain.service.planilha.impl.PlanilhaMessage;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.gratiStore.api_gratiStore.infra.config.planilha.LerPlanilhaMensagemTopologyConfig.QUEUE;

@Component
@RequiredArgsConstructor
public class PlanilhaConsumer {

    private final PlanilhaService planilhaService;

    @RabbitListener(queues = QUEUE)
    public void consumir(PlanilhaMessage evento, Message msg, Channel ch) throws Exception {
        var tag = msg.getMessageProperties().getDeliveryTag();

        if (evento == null) {
            ch.basicNack(tag, false, false);
            return;
        }

        processar(evento);
    }

    private void processar(PlanilhaMessage evento) throws IOException {
        planilhaService.lerPlanilha(evento.conteudo(), evento.lojaId(), evento.semana());
    }
}
