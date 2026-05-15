package com.taskmanager.taskservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.taskservice.dto.TadkDto;
import com.taskmanager.taskservice.entity.TaskEntity;
import com.taskmanager.taskservice.service.TaskService;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
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

    @GetMapping
    public List<TaskEntity> getAllTasks() {
        return service.getAllTasks();
    }
}