package com.worktracker.service.impl;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.Task;
import com.worktracker.model.User;
import com.worktracker.model.Work;
import com.worktracker.model.dto.WorkRequestDTO;
import com.worktracker.repository.WorkRepository;
import com.worktracker.service.TaskService;
import com.worktracker.service.UserService;
import com.worktracker.service.WorkService;
import org.springframework.stereotype.Service;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final UserService userService;
    private final TaskService taskService;

    public WorkServiceImpl(WorkRepository workRepository, UserService userService, TaskService taskService) {
        this.workRepository = workRepository;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public Work addWorkToTask(Long taskId, WorkRequestDTO workRequestDTO) {
        Task task = taskService.getTask(taskId);
        User user = userService.getUser(workRequestDTO.getUserId());
        if (workRequestDTO.getHours() <= 0) {
            throw new WorktrackerException("Invalid hours value");
        }

        Work work = new Work();
        work.setTask(task);
        work.setUser(user);
        work.setHours(workRequestDTO.getHours());
        work.setDate(workRequestDTO.getDate());

        return workRepository.save(work);
    }
}
