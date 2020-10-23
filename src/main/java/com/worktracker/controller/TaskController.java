package com.worktracker.controller;

import com.worktracker.model.Task;
import com.worktracker.model.dto.TaskRequestDTO;
import com.worktracker.model.dto.TaskResponseDTO;
import com.worktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
}
