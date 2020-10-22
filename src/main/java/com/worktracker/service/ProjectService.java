package com.worktracker.service;

import com.worktracker.model.dto.ProjectRequestDTO;

public interface ProjectService {

    void createProject(ProjectRequestDTO projectRequestDTO);

    void updateProjectName(Long id, String name);
}
