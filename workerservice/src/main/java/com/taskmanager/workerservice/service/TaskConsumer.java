package com.taskmanager.workerservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taskmanager.workerservice.entity.TaskEntity;
import com.taskmanager.workerservice.repository.TaskRepository;

@Service
public class TaskConsumer {

    @Autowired
    private TaskRepository repository;

    @Value("${server.port}")
    private String port;

    @RabbitListener(queues = "taskQueue")
    public void consumeTask(TaskEntity task)
            throws InterruptedException {

        try {

            System.out.println("Processing task: " + task.getPayload());

            task.setStatus("PROCESSING");
            task.setWorkerPort(port);

            repository.save(task);

            // Simulate failure
            if (task.getPayload().contains("fail")) {
                throw new RuntimeException("Simulated failure");
            }

            Thread.sleep(3000);

            task.setStatus("COMPLETED");

            repository.save(task);

            System.out.println("Completed by Worker " + port);

        } catch (Exception e) {

            System.out.println("Task failed: " + task.getPayload());

            task.setStatus("FAILED");

            repository.save(task);

            throw e;
        }
    }
}