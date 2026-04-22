package com.taskmanager.workerservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/worker")
public class WokerController {
	@Autowired
	@PostMapping("/process")
	 public Map<String, Object> processTask(@RequestBody Map<String, Object> request) {

        String port = System.getProperty("server.port");

        System.out.println("Task handled by worker on port: " + port);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "COMPLETED");
        response.put("workerPort", port);
        response.put("message", "Task processed successfully");

        return response;
    }
	
	
	

}
