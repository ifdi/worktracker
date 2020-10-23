package com.worktracker.controller;

import com.worktracker.model.Project;
import com.worktracker.model.Task;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.service.ProjectService;
import com.worktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @PostMapping
    public void createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        projectService.createProject(projectRequestDTO);
    }

    @PutMapping("/{id}")
    public void getProject(@PathVariable Integer id, @RequestBody ProjectRequestDTO projectRequestDTO) {
        projectService.updateProjectName(id, projectRequestDTO.getName());
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getTasks(@PathVariable Integer id) {
        Project project = projectService.getProject(id);
        return taskService.getTasks(project);
    }
}
