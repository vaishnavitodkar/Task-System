package com.taskmanager.taskservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.taskmanager.taskservice.dto.TadkDto;
import com.taskmanager.taskservice.entity.TaskEntity;
import com.taskmanager.taskservice.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public TadkDto createTask(TadkDto request) {

        // Step 1: Save as PENDING
        TaskEntity task = new TaskEntity();
        task.setPayload(request.getPayload());
        task.setStatus("PENDING");

        task = repository.save(task);

        // Step 2: PROCESSING
        task.setStatus("PROCESSING");
        repository.save(task);

        // Step 3: Call worker via gateway
        Map response = restTemplate.postForObject(
                "http://localhost:8080/worker/process",
                request,
                Map.class
        );

        // Step 4: Extract response
        String status = (String) response.get("status");
        String workerPort = (String) response.get("workerPort");

        // Step 5: Update DB
        task.setStatus(status);
        task.setWorkerPort(workerPort);
        repository.save(task);

        // Step 6: Return DTO
        TadkDto result = new TadkDto();
        result.setPayload(task.getPayload());
        result.setStatus(status);
        result.setWorkerPort(workerPort);

        return result;
    }

    public TaskEntity getTask(Long id) {
        return repository.findById(id).orElseThrow();
    }
}