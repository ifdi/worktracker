package com.worktracker.service;

import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;

public interface AuthorizationService {

    void validateTokenAuthorization(String token);

    void validateToken(String token);

    LoginResponseDTO getTokenExistingUser(LoginRequestDTO loginRequestDTO);

    void deleteToken(String token);
}
