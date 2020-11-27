package com.worktracker.service.impl;

import com.worktracker.model.Token;
import com.worktracker.model.User;
import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;
import com.worktracker.repository.TokenRepository;
import com.worktracker.service.LoginService;
import com.worktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class LoginServiceImpl implements LoginService {

    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Autowired
    public LoginServiceImpl(TokenRepository tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
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
