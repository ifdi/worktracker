package com.worktracker.model.dto;

public class LoginRequestDTO {

    private String email;
    private char[] password;

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
