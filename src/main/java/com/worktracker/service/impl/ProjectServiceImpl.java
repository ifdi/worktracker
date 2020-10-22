package com.worktracker.service.impl;

import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.repository.ProjectRepository;
import com.worktracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void createProject(ProjectRequestDTO projectRequestDTO) {
        Project project = new Project();
        project.setId(projectRequestDTO.getId());
        project.setName(projectRequestDTO.getName());
    }

    @Override
    public void updateProjectName(Long id, String name) {
        projectRepository.getProjectById(id).setName(name);
    }
}
