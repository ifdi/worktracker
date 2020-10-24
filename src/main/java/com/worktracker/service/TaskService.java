package com.worktracker.service;

import com.worktracker.model.Task;
import com.worktracker.model.dto.TaskRequestDTO;

public interface TaskService {

    Task createTask(TaskRequestDTO taskRequestDTO);

    void updateNote(Long id, String note);

    Task getTask(Long id);
}
