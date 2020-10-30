package com.worktracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worktracker.integration.BaseIT;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.repository.UserRepository;
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

@Sql({"classpath:sql/users/insert-user-data.sql"})
@Sql(value = {"classpath:sql/users/delete-user-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerIT extends BaseIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getEmployeesSuccess() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("User name 1")))
                .andExpect(jsonPath("$[0].email", is("user1@email.com")))
                .andExpect(jsonPath("$[0].type", is("EMPLOYEE")));
    }

    @Test
    public void getEmployeesSuccessNoEmployees() throws Exception {
        userRepository.deleteById(1L);

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteUserSuccess() throws Exception {
        assertTrue(userRepository.existsById(1L));

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/{id}", 1))
                .andExpect(status().isOk());

        assertFalse(userRepository.existsById(1L));
    }

    @Test
    public void deleteUserNonExist() throws Exception {
        assertFalse(userRepository.existsById(2L));

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/{id}", 2))
                .andExpect(status().isBadRequest());

        assertEquals(1, userRepository.count());
    }

    @Test
    public void createUserSuccess() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setEmail("email2@test.com");
        userRequestDTO.setName("Test name 2");
        assertFalse(userRepository.existsByEmail(userRequestDTO.getEmail()));

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("Test name 2")))
                .andExpect(jsonPath("$.email", is("email2@test.com")))
                .andExpect(jsonPath("$.type", is("MANAGER")));

        assertTrue(userRepository.existsByEmail(userRequestDTO.getEmail()));
        assertEquals(2, userRepository.count());
    }

    @Test
    public void createUserExistingEmail() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setEmail("user1@email.com");
        userRequestDTO.setName("Test name 2");
        assertTrue(userRepository.existsByEmail(userRequestDTO.getEmail()));

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(1, userRepository.count());
    }

    @Test
    public void createUserMissingEmail() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setName("Test name 2");

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(1, userRepository.count());
    }
}
