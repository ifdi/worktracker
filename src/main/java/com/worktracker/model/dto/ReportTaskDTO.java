package com.worktracker.model.dto;

import com.worktracker.model.TypeTask;

public class ReportTaskDTO {

    private final Long taskId;
    private final TypeTask typeTask;
    private final String taskName;
    private Double taskHours;

    public ReportTaskDTO(Long taskId, TypeTask typeTask, String taskName, Double taskHours) {
        this.taskId = taskId;
        this.typeTask = typeTask;
        this.taskName = taskName;
        this.taskHours = taskHours;
    }

    public Long getTaskId() {
        return taskId;
    }

    public TypeTask getTypeTask() {
        return typeTask;
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
