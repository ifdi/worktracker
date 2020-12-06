package com.worktracker.controller;

import com.worktracker.model.dto.LoginRequestDTO;
import com.worktracker.model.dto.LoginResponseDTO;
import com.worktracker.service.AuthorizationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Authorization Controller")
@RestController
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public LoginResponseDTO getTokenExistingUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return this.authorizationService.getTokenExistingUser(loginRequestDTO);
    }

    @DeleteMapping("/logout")
    public void deleteToken(@RequestHeader("Authorization") String token) {
        this.authorizationService.deleteToken(token);
    }
}
