package com.worktracker.service.impl;

import com.worktracker.exception.UnauthorizedException;
import com.worktracker.model.UserType;
import com.worktracker.repository.TokenRepository;
import com.worktracker.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthorizationServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void validateTokenAuthorization(String token) {
        validateToken(token);
        if (tokenRepository.getUserTypeByToken(token) == UserType.EMPLOYEE) {
            throw new UnauthorizedException("Unauthorized User");
        }
    }

    @Override
    public void validateToken(String token) {
        if (!tokenRepository.existsByToken(token)) {
            throw new UnauthorizedException("Non Existing Token");
        }
    }
}
