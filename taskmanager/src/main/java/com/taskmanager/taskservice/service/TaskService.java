package com.taskmanager.taskservice.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.taskservice.dto.TadkDto;
import com.taskmanager.taskservice.entity.TaskEntity;
import com.taskmanager.taskservice.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public TadkDto createTask(TadkDto request) {

        // Save task
        TaskEntity task = new TaskEntity();

        task.setPayload(request.getPayload());
        task.setStatus("PENDING");

        task = repository.save(task);

        // Send task to RabbitMQ
        rabbitTemplate.convertAndSend(
            "taskExchange",
            "taskRoutingKey",
            task
        );

        System.out.println("Task sent to RabbitMQ: " + task.getPayload());

        // Return response immediately
        TadkDto result = new TadkDto();

        result.setPayload(task.getPayload());
        result.setStatus(task.getStatus());

        return result;
    }

    public List<TaskEntity> getAllTasks() {
        return repository.findAll();
    }

    public TaskEntity getTask(Long id) {
        return repository.findById(id).orElseThrow();
    }
}