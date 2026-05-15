package com.taskmanager.workerservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DeadLetterConsumer {

    @RabbitListener(queues = "deadLetterQueue")
    public void consumeFailedMessages(Object message) {

        System.out.println("Message moved to DLQ");
        System.out.println(message);
    }
}