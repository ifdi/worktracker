package com.worktracker.controller;

import com.worktracker.model.Task;
import com.worktracker.model.dto.TaskRequestDTO;
import com.worktracker.model.dto.TaskResponseDTO;
import com.worktracker.model.dto.WorkRequestDTO;
import com.worktracker.service.TaskService;
import com.worktracker.service.WorkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Task Controller")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final WorkService workService;

    @Autowired
    public TaskController(TaskService taskService, WorkService workService) {
        this.taskService = taskService;
        this.workService = workService;
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        Task task = taskService.createTask(taskRequestDTO);
        return new TaskResponseDTO(task);
    }

    @PutMapping("/{id}")
    public void updateNote(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.updateNote(id, taskRequestDTO.getNote());
    }

    @PostMapping("/{id}/works")
    public void addWorkToTask(@PathVariable Long id, @RequestBody WorkRequestDTO workRequestDTO) {
        workService.addWorkToTask(id, workRequestDTO);
    }
}
