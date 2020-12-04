package com.worktracker.unit.service;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.User;
import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;
import com.worktracker.repository.TokenRepository;
import com.worktracker.service.LoginService;
import com.worktracker.service.UserService;
import com.worktracker.service.impl.LoginServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    private LoginService loginService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        loginService = new LoginServiceImpl(tokenRepository, userService);
    }

    @Test
    public void getTokenExistingUserSuccess() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("Test email");
        loginRequestDTO.setPassword("Test pass".toCharArray());
        User user = new User();
        when(userService.getUserByEmailAndPass(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()))
                .thenReturn(user);
        when(tokenRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        LoginResponseDTO result = loginService.getTokenExistingUser(loginRequestDTO);

        verify(userService, times(1)).getUserByEmailAndPass(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        verify(tokenRepository, times(1)).save(any());
        assertNotNull(result);
    }

    @Test(expected = WorktrackerException.class)
    public void getTokenExistingUserNonSuccess() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("Test email");
        loginRequestDTO.setPassword("Test pass".toCharArray());
        when(userService.getUserByEmailAndPass(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()))
                .thenThrow(new WorktrackerException("User not exist"));

        loginService.getTokenExistingUser(loginRequestDTO);

        verify(userService, times(1)).getUserByEmailAndPass(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        verify(tokenRepository, times(0)).save(any());
    }
}
