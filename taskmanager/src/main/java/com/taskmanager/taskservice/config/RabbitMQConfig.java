package com.taskmanager.taskservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "taskQueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public MessageConverter Jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }  
}
