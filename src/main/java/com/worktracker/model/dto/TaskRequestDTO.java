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

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
