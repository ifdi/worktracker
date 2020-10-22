package com.worktracker.service;

import com.worktracker.model.dto.ProjectRequestDTO;

public interface ProjectService {

    void createProject(ProjectRequestDTO projectRequestDTO);

    void updateProjectName(Integer id, String name);
}
