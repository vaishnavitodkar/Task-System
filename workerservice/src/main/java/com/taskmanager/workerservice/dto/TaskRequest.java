package com.taskmanager.workerservice.dto;

import lombok.Data;

@Data
public class TaskRequest {
	private Long Id;
	private String payload;
}
