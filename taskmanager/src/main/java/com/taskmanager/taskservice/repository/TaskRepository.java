package com.taskmanager.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taskmanager.taskservice.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}