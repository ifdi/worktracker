package com.worktracker.repository.projection;

import com.worktracker.model.TaskType;

import java.time.LocalDate;

public interface ReportByUserProjection {

    Integer getProjectId();

    String getProjectName();

    TaskType getTaskType();

    String getTaskName();

    Double getHours();

    LocalDate getDate();

    Long getTaskId();

}
