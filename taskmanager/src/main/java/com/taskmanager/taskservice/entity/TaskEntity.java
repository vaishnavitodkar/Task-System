package com.taskmanager.taskservice.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class TaskEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payload;

    private String status; // PENDING, PROCESSING, COMPLETED

    private String workerPort; // NEW

    public Long getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkerPort() {
        return workerPort;
    }

    public void setWorkerPort(String workerPort) {
        this.workerPort = workerPort;
    }
}