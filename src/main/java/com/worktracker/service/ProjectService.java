package com.worktracker.service;

import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;

import java.util.List;

public interface ProjectService {

    Project createProject(ProjectRequestDTO projectRequestDTO);

    void updateProjectName(Integer id, String name);

    Project getProject(Integer id);

    List<Project> getAllProjects();
}
