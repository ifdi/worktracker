package com.worktracker.service;

import com.worktracker.model.Project;
import com.worktracker.model.Task;
import com.worktracker.model.dto.TaskRequestDTO;
import com.worktracker.model.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRequestDTO taskRequestDTO);

    void updateTaskInformation(Long id, String note);

}
