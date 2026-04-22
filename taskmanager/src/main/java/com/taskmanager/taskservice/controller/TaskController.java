package com.taskmanager.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.taskmanager.taskservice.dto.TadkDto;
import com.taskmanager.taskservice.entity.TaskEntity;
import com.taskmanager.taskservice.service.TaskService;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public TadkDto createTask(@RequestBody TadkDto task) {
        return service.createTask(task);
    }

    @GetMapping("/{id}")
    public TaskEntity getTask(@PathVariable Long id) {
        return service.getTask(id);
    }
}