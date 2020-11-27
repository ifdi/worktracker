package com.worktracker.controller;

import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;
import com.worktracker.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Login Controller")
@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginResponseDTO getTokenExistingUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return loginService.getTokenExistingUser(loginRequestDTO);
    }
}
