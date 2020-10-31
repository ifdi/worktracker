package com.worktracker.service.impl;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.repository.ProjectRepository;
import com.worktracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(ProjectRequestDTO projectRequestDTO) {
        if (projectRequestDTO.getId() == null || projectRepository.existsById(projectRequestDTO.getId())) {
            throw new WorktrackerException("Project is not provided or is invalid.");
        }
        Project project = new Project();
        project.setId(projectRequestDTO.getId());
        project.setName(projectRequestDTO.getName());

        return projectRepository.save(project);
    }

    @Override
    public void updateProjectName(Integer id, String name) {
        Project project = getProject(id);
        project.setName(name);
        projectRepository.save(project);
    }

    @Override
    public Project getProject(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new WorktrackerException("Project not found"));
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
