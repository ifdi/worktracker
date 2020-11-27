package com.worktracker.unit.service;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.Task;
import com.worktracker.model.User;
import com.worktracker.model.Work;
import com.worktracker.model.dto.WorkRequestDTO;
import com.worktracker.repository.WorkRepository;
import com.worktracker.service.TaskService;
import com.worktracker.service.UserService;
import com.worktracker.service.WorkService;
import com.worktracker.service.impl.WorkServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkServiceTest {

    private WorkService workService;

    @Mock
    private WorkRepository workRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaskService taskService;

    @Before
    public void setUp() {
        workService = new WorkServiceImpl(workRepository, userService, taskService);
    }

    @Test
    public void addWorkToTaskSuccess() {
        WorkRequestDTO workRequestDTO = new WorkRequestDTO();
        workRequestDTO.setUserId(1L);
        workRequestDTO.setHours(1.0);
        LocalDate date = LocalDate.parse("2020-11-05");
        workRequestDTO.setDate(date);
        Task task = new Task();
        User user = new User();
        when(taskService.getTask(1L)).thenReturn(task);
        when(userService.getUserById(1L)).thenReturn(user);
        when(workRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Work result = workService.addWorkToTask(1L, workRequestDTO);

        assertNotNull(result);
        assertEquals(task, result.getTask());
        assertEquals(user, result.getUser());
        assertEquals(date, result.getDate());
        assertEquals(1.0, result.getHours(), 0);
    }

    @Test(expected = WorktrackerException.class)
    public void addWorkToTaskFailNegativeHours() {
        WorkRequestDTO workRequestDTO = new WorkRequestDTO();
        workRequestDTO.setHours(-1);

        workService.addWorkToTask(1L, workRequestDTO);

        verify(workRepository, times(0)).save(any());
    }

}
