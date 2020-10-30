package com.worktracker.unit.service;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.Project;
import com.worktracker.model.Task;
import com.worktracker.model.TaskType;
import com.worktracker.model.dto.TaskRequestDTO;
import com.worktracker.repository.TaskRepository;
import com.worktracker.service.ProjectService;
import com.worktracker.service.TaskService;
import com.worktracker.service.impl.TaskServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectService projectService;

    @Before
    public void setUp() {
        taskService = new TaskServiceImpl(taskRepository, projectService);
    }

    @Test
    public void createTaskSuccess() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setProjectID(1);
        taskRequestDTO.setName("Task Test Name");
        taskRequestDTO.setType(TaskType.BIM);
        taskRequestDTO.setNote("Note");
        Project project = new Project();
        when(projectService.getProject(1)).thenReturn(project);
        when(taskRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Task result = taskService.createTask(taskRequestDTO);

        assertNotNull(result);
        assertEquals(project, result.getProject());
        assertEquals("Task Test Name", result.getName());
        assertEquals(TaskType.BIM, result.getTaskType());
        assertEquals("Note", result.getNote());
    }

    @Test
    public void updateNoteSuccess() {
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.updateNote(1L, "New note Test");

        verify(taskRepository, times(1)).save(any());
        assertEquals("New note Test", task.getNote());
    }

    @Test(expected = WorktrackerException.class)
    public void updateNoteNonExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        taskService.updateNote(1L, "New note Test");

        verify(taskRepository, times(0)).save(any());
    }

    @Test
    public void getTaskExist() {
        Task task = new Task();
        task.setName("Task Test Name");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTask(1L);

        assertNotNull(result);
        assertEquals("Task Test Name", result.getName());
    }

    @Test(expected = WorktrackerException.class)
    public void getTaskNonExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Task result = taskService.getTask(1L);
    }
}
