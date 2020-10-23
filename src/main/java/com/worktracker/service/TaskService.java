package com.worktracker.service;

import com.worktracker.model.Project;
import com.worktracker.model.Task;
import com.worktracker.model.dto.TaskRequestDTO;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRequestDTO taskRequestDTO);

    void updateTaskInformation(Long id, String note);

    List<Task> getTasks(Project project);
}
