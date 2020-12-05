package com.worktracker.unit.service;

import com.worktracker.exception.UnauthorizedException;
import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;
import com.worktracker.repository.TokenRepository;
import com.worktracker.service.AuthorizationService;
import com.worktracker.service.UserService;
import com.worktracker.service.impl.AuthorizationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        authorizationService = new AuthorizationServiceImpl(tokenRepository, userService);
    }

    @Test
    public void validateTokenSuccess() {
        String token = "token-Test";
        when(tokenRepository.existsByToken(any())).thenReturn(true);

        authorizationService.validateToken(token);

        verify(tokenRepository, times(1)).existsByToken(any());
    }

    @Test(expected = UnauthorizedException.class)
    public void validateTokenNonExist() {
        String token = "token-Test";
        when(tokenRepository.existsByToken(any())).thenReturn(false);

        authorizationService.validateToken(token);
    }

    @Test
    public void validateTokenAuthorizationSuccess() {
        String token = "token-Test";
        when(tokenRepository.existsByToken(any())).thenReturn(true);
        UserType userType = UserType.MANAGER;
        when(tokenRepository.getUserTypeByToken(any())).thenReturn(userType);

        authorizationService.validateTokenAuthorization(token);

        verify(tokenRepository, times(1)).existsByToken(any());
        verify(tokenRepository, times(1)).getUserTypeByToken(any());
    }

    @Test(expected = UnauthorizedException.class)
    public void validateTokenAuthorizationNotAuth() {
        String token = "token-Test";
        when(tokenRepository.existsByToken(any())).thenReturn(true);
        UserType userType = UserType.EMPLOYEE;
        when(tokenRepository.getUserTypeByToken(any())).thenReturn(userType);

        authorizationService.validateTokenAuthorization(token);
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

        LoginResponseDTO result = authorizationService.getTokenExistingUser(loginRequestDTO);

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

        authorizationService.getTokenExistingUser(loginRequestDTO);
    }

    @Test
    public void deleteToken() {
        String token = "token-Test";

        authorizationService.deleteToken(token);

        verify(tokenRepository, times(1)).deleteByToken(token);
    }
}
