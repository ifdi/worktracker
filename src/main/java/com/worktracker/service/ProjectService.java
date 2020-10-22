package com.worktracker.service;

import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;

public interface ProjectService {

    Project createProject(ProjectRequestDTO projectRequestDTO);

    void updateProjectName(Integer id, String name);
}
