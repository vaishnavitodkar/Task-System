package com.taskmanager.taskservice.dto;

import lombok.Data;

@Data
public class TadkDto {
    private String payload;
    private String status;
    private String workerPort;
}