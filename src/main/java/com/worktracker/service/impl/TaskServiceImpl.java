package com.worktracker.service.impl;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.Project;
import com.worktracker.model.Task;
import com.worktracker.model.dto.TaskRequestDTO;
import com.worktracker.repository.TaskRepository;
import com.worktracker.service.ProjectService;
import com.worktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    @Override
    public Task createTask(TaskRequestDTO taskRequestDTO) {
        Project project = projectService.getProject(taskRequestDTO.getProjectID());
        Task task = new Task();
        task.setProject(project);
        task.setName(taskRequestDTO.getName());
        task.setTaskType(taskRequestDTO.getType());
        task.setNote(taskRequestDTO.getNote());

        return taskRepository.save(task);
    }

    @Override
    public void updateNote(Long id, String note) {
        Task task = getTask(id);
        task.setNote(note);
        taskRepository.save(task);
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new WorktrackerException("Task not found"));
    }
}
