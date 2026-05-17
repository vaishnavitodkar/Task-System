package com.taskmanager.workerservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Value("${server.port}")
    private String port;

    @PostMapping("/process")
    public Map<String, Object> processTask(
            @RequestBody Map<String, Object> request
    ) throws InterruptedException {

        System.out.println("🔥 Task handled by worker on port: " + port);

        // Simulate processing
        Thread.sleep(2000);

        Map<String, Object> response = new HashMap<>();

        response.put("status", "COMPLETED");
        response.put("workerPort", port);

        return response;
    }
}