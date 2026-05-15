package com.taskmanager.workerservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Main Queue
    public static final String TASK_QUEUE = "taskQueue";

    // Dead Letter Queue
    public static final String DLQ = "deadLetterQueue";

    // Exchange
    public static final String EXCHANGE = "taskExchange";

    // Routing Keys
    public static final String ROUTING_KEY = "taskRoutingKey";
    public static final String DLQ_ROUTING_KEY = "deadLetterRoutingKey";

    // MAIN QUEUE
    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE, true, false, false,
                java.util.Map.of(
                        "x-dead-letter-exchange", EXCHANGE,
                        "x-dead-letter-routing-key", DLQ_ROUTING_KEY
                ));
    }

    // DLQ
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ);
    }

    // Exchange
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    // Binding Main Queue
    @Bean
    public Binding taskBinding() {
        return BindingBuilder
                .bind(taskQueue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    // Binding DLQ
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(exchange())
                .with(DLQ_ROUTING_KEY);
    }
}