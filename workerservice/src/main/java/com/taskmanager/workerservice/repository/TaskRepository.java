package com.taskmanager.workerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.workerservice.entity.TaskEntity;

@Repository
public interface TaskRepository
        extends JpaRepository<TaskEntity, Long> {

}