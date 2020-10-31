package com.worktracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worktracker.integration.BaseIT;
import com.worktracker.model.TaskType;
import com.worktracker.model.dto.TaskRequestDTO;
import com.worktracker.model.dto.WorkRequestDTO;
import com.worktracker.repository.ProjectRepository;
import com.worktracker.repository.TaskRepository;
import com.worktracker.repository.UserRepository;
import com.worktracker.repository.WorkRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"classpath:sql/tasks/insert-task-data.sql"})
@Sql(value = {"classpath:sql/tasks/delete-task-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TaskControllerIT extends BaseIT {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createTaskSuccess() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setProjectID(1);
        taskRequestDTO.setName("task name 2");
        taskRequestDTO.setType(TaskType.RC);
        taskRequestDTO.setNote("note 2");

        assertEquals(1, taskRepository.count());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("task name 2")))
                .andExpect(jsonPath("$.type", is("RC")))
                .andExpect(jsonPath("$.note", is("note 2")));

        assertEquals(2, taskRepository.count());
    }

    @Test
    public void createTaskNonExistProject() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setProjectID(2);

        assertEquals(1, taskRepository.count());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(1, taskRepository.count());
    }

    @Test
    public void updateTaskNoteSuccess() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setNote("new note");
        assertTrue(taskRepository.existsById(1L));

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/tasks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isOk());

        assertTrue(taskRepository.existsByNote("new note"));
    }

    @Test
    public void updateTaskNoteNonExist() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setNote("new note");
        assertFalse(taskRepository.existsById(2L));

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/tasks/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isBadRequest());

        assertFalse(taskRepository.existsByNote("new note"));
    }

    @Test
    public void addWorkToTaskSuccess() throws Exception {
        WorkRequestDTO workRequestDTO = new WorkRequestDTO();
        workRequestDTO.setUserId(1L);
        workRequestDTO.setHours(5);
        workRequestDTO.setDate(LocalDate.parse("2020-10-30"));
        assertEquals(0, workRepository.count());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/tasks/{id}/works", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workRequestDTO)))
                .andExpect(status().isOk());

        assertEquals(1, workRepository.count());
    }

    @Test
    public void addWorkToTaskNonExistTask() throws Exception {
        WorkRequestDTO workRequestDTO = new WorkRequestDTO();
        workRequestDTO.setUserId(1L);
        workRequestDTO.setHours(5);
        workRequestDTO.setDate(LocalDate.parse("2020-10-30"));
        assertEquals(0, workRepository.count());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/tasks/{id}/works", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(0, workRepository.count());
    }

    @Test
    public void addWorkToTaskInvalidHours() throws Exception {
        WorkRequestDTO workRequestDTO = new WorkRequestDTO();
        workRequestDTO.setUserId(1L);
        workRequestDTO.setHours(-5);
        workRequestDTO.setDate(LocalDate.parse("2020-10-30"));
        assertEquals(0, workRepository.count());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/tasks/{id}/works", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(0, workRepository.count());
    }
}
