package com.worktracker.model.dto;

import com.worktracker.model.Task;
import com.worktracker.model.TypeTask;

public class TaskResponseDTO {

    private Long id;
    private String name;
    private TypeTask type;
    private String note;

    public TaskResponseDTO(Task task) {
        this.setId(task.getId());
        this.setName(task.getName());
        this.setType(task.getTypeTask());
        this.setNote(task.getNote());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TypeTask type) {
        this.type = type;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
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
