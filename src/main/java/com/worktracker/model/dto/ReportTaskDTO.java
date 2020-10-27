package com.worktracker.model.dto;

import com.worktracker.model.TaskType;

import java.util.Objects;

public class ReportTaskDTO {

    private final Long taskId;
    private final TaskType taskType;
    private final String taskName;
    private Double taskHours;

    public ReportTaskDTO(Long taskId, TaskType taskType, String taskName, Double taskHours) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.taskName = taskName;
        this.taskHours = Objects.requireNonNullElse(taskHours, 0.0);
    }

    public Long getTaskId() {
        return taskId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void addHours(Double taskHours) {
        this.taskHours += taskHours;
    }

    public Double getTaskHours() {
        return taskHours;
    }
}
