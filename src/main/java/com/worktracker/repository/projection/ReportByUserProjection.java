package com.worktracker.repository.projection;

import com.worktracker.model.TypeTask;

import java.time.LocalDate;

public interface ReportByUserProjection {

    Integer getProjectId();

    String getProjectName();

    TypeTask getTypeTask();

    String getTaskName();

    Double getHours();

    LocalDate getDate();

    Long getTaskId();

}
