package com.worktracker.model.dto;

import com.worktracker.model.Token;

public class LoginResponseDTO {

    String token;

    public LoginResponseDTO(Token token) {
        this.token = token.getToken();
    }

    public String getToken() {
        return token;
    }

}
