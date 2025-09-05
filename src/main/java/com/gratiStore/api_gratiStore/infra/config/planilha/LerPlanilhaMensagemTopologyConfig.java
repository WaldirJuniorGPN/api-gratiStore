package com.gratiStore.api_gratiStore.infra.config.planilha;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LerPlanilhaMensagemTopologyConfig {

    public static final String EXCHANGE = "planilha.exchange";
    public static final String QUEUE = "planilha.queue";
    public static final String ROUTING_KEY = "planilha.novo";
    public static final String DLX = "planilha.dlx";
    public static final String DLQ = "planilha.dlq";

    @Bean
    DirectExchange planilhaExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX, true, false);
    }

    @Bean
    Queue planilhaQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .build();
    }

    @Bean
    Queue planilhaDeadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    Binding planilhaBinding() {
        return BindingBuilder.bind(planilhaQueue())
                .to(planilhaExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    Binding planilhaDlqBinding() {
        return BindingBuilder.bind(planilhaDeadLetterQueue())
                .to(deadLetterExchange())
                .with(ROUTING_KEY);
    }
}
