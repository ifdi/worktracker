package com.worktracker.model.dto;

import com.worktracker.model.Project;

public class ProjectResponseDTO {

    private final Integer id;
    private final String name;

    public ProjectResponseDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
