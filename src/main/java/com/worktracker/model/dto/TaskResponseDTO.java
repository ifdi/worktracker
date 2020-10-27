package com.worktracker.model.dto;

import com.worktracker.model.Task;
import com.worktracker.model.TaskType;

public class TaskResponseDTO {

    private final Long id;
    private final String name;
    private final TaskType type;
    private final String note;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.type = task.getTaskType();
        this.note = task.getNote();
    }

    public Long getId() {
        return id;
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
