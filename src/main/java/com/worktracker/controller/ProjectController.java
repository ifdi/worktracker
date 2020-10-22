package com.worktracker.controller;

import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void createProject(@PathVariable Long id, @RequestBody String name) {
        projectService.updateProjectName(id, name);
    }
}
