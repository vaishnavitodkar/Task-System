package com.taskmanager.workerservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TASK_QUEUE = "taskQueue";
    public static final String DLQ = "deadLetterQueue";
    public static final String EXCHANGE = "taskExchange";
    public static final String ROUTING_KEY = "taskRoutingKey";
    public static final String DLQ_ROUTING_KEY = "deadLetterRoutingKey";

    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE, true, false, false,
                java.util.Map.of(
                        "x-dead-letter-exchange", EXCHANGE,
                        "x-dead-letter-routing-key", DLQ_ROUTING_KEY
                ));
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding taskBinding() {
        return BindingBuilder
                .bind(taskQueue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(exchange())
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}