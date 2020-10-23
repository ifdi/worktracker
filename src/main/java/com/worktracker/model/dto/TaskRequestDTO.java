package com.worktracker.model.dto;

import com.worktracker.model.TypeTask;

public class TaskRequestDTO {

    private Integer projectID;
    private String name;
    private TypeTask type;
    private String note;

    public Integer getProjectID() {
        return projectID;
    }

    public String getName() {
        return name;
    }

    public TypeTask getType() {
        return type;
    }

    public String getNote() {
        return note;
    }
}
