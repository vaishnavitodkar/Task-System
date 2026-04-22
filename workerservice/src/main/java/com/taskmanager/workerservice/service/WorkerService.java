package com.taskmanager.workerservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class WorkerService {

    public Map<String, Object> processTask(Map<String, Object> request) {

        String port = System.getProperty("server.port");

        System.out.println("🔥 Task handled by worker on port: " + port);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "COMPLETED");
        response.put("workerPort", port);
        response.put("payload", request.get("payload"));

        return response;
    }
}