package com.worktracker.unit.service;

import com.worktracker.exception.UnauthorizedException;
import com.worktracker.model.UserType;
import com.worktracker.repository.TokenRepository;
import com.worktracker.service.AuthorizationService;
import com.worktracker.service.impl.AuthorizationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    @Mock
    private TokenRepository tokenRepository;

    @Before
    public void setUp() {
        authorizationService = new AuthorizationServiceImpl(tokenRepository);
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

        verify(tokenRepository, times(1)).existsByToken(any());
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

        verify(tokenRepository, times(1)).existsByToken(any());
        verify(tokenRepository, times(1)).getUserTypeByToken(any());
    }

}
