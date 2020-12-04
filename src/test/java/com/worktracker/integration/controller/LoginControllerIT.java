package com.worktracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worktracker.integration.BaseIT;
import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.repository.TokenRepository;
import com.worktracker.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"classpath:sql/login/insert-login-data.sql"})
@Sql(value = {"classpath:sql/login/delete-login-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginControllerIT extends BaseIT {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getTokenExistingUser() throws Exception {
        assertTrue(userRepository.existsById(1L));
        assertFalse(tokenRepository.existsByUserId(1L));
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("user1@email.com");
        loginRequestDTO.setPassword("password1".toCharArray());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        assertTrue(tokenRepository.existsByUserId(1L));
    }

    @Test
    public void getTokenExistingUserWrongPassword() throws Exception {
        assertTrue(userRepository.existsById(1L));
        assertFalse(tokenRepository.existsByUserId(1L));
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("user1@email.com");
        loginRequestDTO.setPassword("password2".toCharArray());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isBadRequest());

        assertFalse(tokenRepository.existsByUserId(1L));
    }
}
