package com.worktracker.repository.projection;

import com.worktracker.model.TaskType;

public interface ReportByProjectProjection {

    Long getTaskId();

    TaskType getTaskType();

    String getTaskName();

    Double getHours();

    String getUserName();

    Long getUserId();

}
