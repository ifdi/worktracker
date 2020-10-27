package com.worktracker.model.dto;

import com.worktracker.model.TaskType;

public class TaskRequestDTO {

    private Integer projectID;
    private String name;
    private TaskType type;
    private String note;

    public Integer getProjectID() {
        return projectID;
    }

    public String getName() {
        return name;
    }

    public TaskType getType() {
        return type;
    }

    public String getNote() {
        return note;
    }
}
