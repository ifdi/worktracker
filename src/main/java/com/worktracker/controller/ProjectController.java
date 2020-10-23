package com.worktracker.controller;

import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.model.dto.TaskResponseDTO;
import com.worktracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public void createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        projectService.createProject(projectRequestDTO);
    }

    @PutMapping("/{id}")
    public void updateProjectName(@PathVariable Integer id, @RequestBody ProjectRequestDTO projectRequestDTO) {
        projectService.updateProjectName(id, projectRequestDTO.getName());
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}/tasks")
    public List<TaskResponseDTO> getTasks(@PathVariable Integer id) {
        Project project = projectService.getProject(id);
        return project.getTasks().stream()
                .map(TaskResponseDTO::new)
                .collect(Collectors.toList());
    }
}
