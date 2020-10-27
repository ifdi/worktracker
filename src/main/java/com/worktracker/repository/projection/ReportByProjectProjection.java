package com.worktracker.repository.projection;

import com.worktracker.model.TypeTask;

public interface ReportByProjectProjection {

    Long getTaskId();

    TypeTask getTypeTask();

    String getTaskName();

    Double getHours();

    String getUserName();

    Long getUserId();

}
