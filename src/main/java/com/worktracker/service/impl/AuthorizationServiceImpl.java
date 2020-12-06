package com.worktracker.service.impl;

import com.worktracker.exception.UnauthorizedException;
import com.worktracker.model.Token;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;
import com.worktracker.repository.TokenRepository;
import com.worktracker.service.AuthorizationService;
import com.worktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Autowired
    public AuthorizationServiceImpl(TokenRepository tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
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

    @Override
    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public LoginResponseDTO getTokenExistingUser(LoginRequestDTO loginRequestDTO) {
        User user = userService.getUserByEmailAndPass(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        Token token = new Token();
        token.setToken(generateToken());
        token.setUserId(user.getId());
        tokenRepository.save(token);

        return new LoginResponseDTO(token);
    }

    private String generateToken() {
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
        SecureRandom random = new SecureRandom();
        int count = 16;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        if (tokenRepository.existsByToken(sb.toString())) {
            generateToken();
        }
        return sb.toString();
    }
}
