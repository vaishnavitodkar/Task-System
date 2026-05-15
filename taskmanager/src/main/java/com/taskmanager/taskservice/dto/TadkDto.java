package com.taskmanager.taskservice.dto;

import lombok.Data;

@Data
public class TadkDto {

   private String payload;
    private String status;

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

    private String workerPort;
}