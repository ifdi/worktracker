package com.worktracker.controller;

import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.model.dto.ProjectResponseDTO;
import com.worktracker.model.dto.TaskResponseDTO;
import com.worktracker.service.AuthorizationService;
import com.worktracker.service.ProjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Project Controller")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final AuthorizationService authorizationService;

    @Autowired
    public ProjectController(ProjectService projectService, AuthorizationService authorizationService) {
        this.projectService = projectService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public void createProject(@RequestBody ProjectRequestDTO projectRequestDTO,
                              @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
        authorizationService.validateTokenAuthorization(token);
        projectService.createProject(projectRequestDTO);
    }

    @PutMapping("/{id}")
    public void updateProjectName(@PathVariable Integer id,
                                  @RequestBody ProjectRequestDTO projectRequestDTO,
                                  @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
        projectService.updateProjectName(id, projectRequestDTO.getName());
    }

    @GetMapping
    public List<ProjectResponseDTO> getAllProjects(@RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);

        return projectService.getAllProjects().stream()
                .map(ProjectResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/tasks")
    public List<TaskResponseDTO> getTasks(@PathVariable Integer id,
                                          @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);

        Project project = projectService.getProject(id);
        return project.getTasks().stream()
                .map(TaskResponseDTO::new)
                .collect(Collectors.toList());
    }
}
