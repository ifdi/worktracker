package com.worktracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worktracker.integration.BaseIT;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.repository.ProjectRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"classpath:sql/projects/insert-project-data.sql"})
@Sql(value = {"classpath:sql/projects/delete-project-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectControllerIT extends BaseIT {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createProjectSuccess() throws Exception {
        assertFalse(projectRepository.existsById(3));
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setId(3);
        projectRequestDTO.setName("project name 3");

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDTO)))
                .andExpect(status().isOk());

        assertEquals(3, projectRepository.count());
        assertTrue(projectRepository.existsById(3));
    }

    @Test
    public void createProjectExistingID() throws Exception {
        assertTrue(projectRepository.existsById(1));

        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setId(1);
        projectRequestDTO.setName("project name 1");

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(2, projectRepository.count());
    }

    @Test
    public void createProjectMissingID() throws Exception {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("project name 1");

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(2, projectRepository.count());
    }

    @Test
    public void updateProjectNameSuccess() throws Exception {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("project name 3");
        assertTrue(projectRepository.existsById(1));

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/projects/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDTO)))
                .andExpect(status().isOk());

        assertTrue(projectRepository.existsByName("project name 3"));
    }

    @Test
    public void updateProjectNameNonExist() throws Exception {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("project name 3");
        assertFalse(projectRepository.existsById(3));

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/projects/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDTO)))
                .andExpect(status().isBadRequest());

        assertFalse(projectRepository.existsByName("project name 3"));
    }

    @Test
    public void getAllProjectsSuccess() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("project name 1")));
    }

    @Test
    public void getTasksSuccess() throws Exception {
        assertTrue(projectRepository.existsById(1));

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/projects/{id}/tasks", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("task name 1")))
                .andExpect(jsonPath("$[0].note", is("note 1")))
                .andExpect(jsonPath("$[0].type", is("RC")));
    }

    @Test
    public void getTasksSuccessNoTasks() throws Exception {
        assertTrue(projectRepository.existsById(2));

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/projects/{id}/tasks", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getTasksNonExistProjectID() throws Exception {
        assertFalse(projectRepository.existsById(3));

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/projects/{id}/tasks", 3))
                .andExpect(status().isBadRequest());
    }
}
