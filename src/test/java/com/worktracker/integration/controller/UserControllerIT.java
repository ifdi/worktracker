package com.worktracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worktracker.integration.BaseIT;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.UpdatePasswordDTO;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.repository.TokenRepository;
import com.worktracker.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

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
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getEmployeesSuccess() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/employees")
                        .header("Authorization", "OQ_2nG-BYHlhQlCC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("User name 2")))
                .andExpect(jsonPath("$[0].email", is("user2@email.com")))
                .andExpect(jsonPath("$[0].type", is("EMPLOYEE")));
    }

    @Test
    @Transactional
    public void getEmployeesSuccessNoEmployees() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        tokenRepository.deleteByUserId(2L);
        userRepository.deleteById(2L);

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/employees")
                        .header("Authorization", "OQ_2nG-BYHlhQlCC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getEmployeesSuccessMissingHeader() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/employees"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserSuccess() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        assertTrue(userRepository.existsById(2L));

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/{id}", 2)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC"))
                .andExpect(status().isOk());

        assertFalse(userRepository.existsById(2L));
        assertFalse(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCZ"));
    }

    @Test
    public void deleteUserNonExist() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        assertFalse(userRepository.existsById(3L));

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/{id}", 3)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC"))
                .andExpect(status().isBadRequest());

        assertEquals(2, userRepository.count());
    }

    @Test
    public void deleteUserMissingHeader() throws Exception {
        assertTrue(userRepository.existsById(2L));

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/{id}", 2))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserUnauthorized() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCZ"));
        assertTrue(userRepository.existsById(2L));

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/{id}", 2)
                        .header("Authorization", "OQ_2nG-BYHlhQlCZ"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createUserSuccess() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setEmail("email3@test.com");
        userRequestDTO.setName("Test name 3");
        assertFalse(userRepository.existsByEmail(userRequestDTO.getEmail()));

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("Test name 3")))
                .andExpect(jsonPath("$.email", is("email3@test.com")))
                .andExpect(jsonPath("$.type", is("MANAGER")));

        assertTrue(userRepository.existsByEmail(userRequestDTO.getEmail()));
        assertEquals(3, userRepository.count());
    }

    @Test
    public void createUserExistingEmail() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setEmail("user1@email.com");
        userRequestDTO.setName("Test name 3");
        assertTrue(userRepository.existsByEmail(userRequestDTO.getEmail()));

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(2, userRepository.count());
    }

    @Test
    public void createUserMissingEmail() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setName("Test name 3");

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest());

        assertEquals(2, userRepository.count());
    }

    @Test
    public void createUserMissingHeader() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setType(UserType.MANAGER);
        userRequestDTO.setEmail("email3@test.com");
        userRequestDTO.setName("Test name 3");
        assertFalse(userRepository.existsByEmail(userRequestDTO.getEmail()));

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void changePasswordSuccess() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setOldPassword("password1".toCharArray());
        updatePasswordDTO.setNewPassword("newPassword1".toCharArray());

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/users/{id}/password", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isOk());

        User user = userRepository.getUserById(1L);
        assertArrayEquals("newPassword1".toCharArray(), user.getPassword());
    }

    @Test
    public void changePasswordMissingHeader() throws Exception {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setOldPassword("password1".toCharArray());
        updatePasswordDTO.setNewPassword("newPassword1".toCharArray());

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/users/{id}/password", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isBadRequest());

        User user = userRepository.getUserById(1L);
        assertArrayEquals("password1".toCharArray(), user.getPassword());
    }

    @Test
    public void changePasswordWrongPassword() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setOldPassword("invalid password".toCharArray());
        updatePasswordDTO.setNewPassword("newPassword1".toCharArray());

        mvc.perform(
                MockMvcRequestBuilders
                        .put("/users/{id}/password", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isBadRequest());
    }

}
