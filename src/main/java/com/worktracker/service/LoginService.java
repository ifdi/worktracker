package com.worktracker.service;

import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;

public interface LoginService {

    LoginResponseDTO getTokenExistingUser(LoginRequestDTO loginRequestDTO);

}