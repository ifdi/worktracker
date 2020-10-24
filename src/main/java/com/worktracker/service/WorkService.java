package com.worktracker.service;

import com.worktracker.model.Work;
import com.worktracker.model.dto.WorkRequestDTO;

public interface WorkService {

    Work addWorkToTask(Long taskId, WorkRequestDTO workRequestDTO);
}
