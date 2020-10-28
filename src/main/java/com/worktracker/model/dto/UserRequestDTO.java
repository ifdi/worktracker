package com.worktracker.model.dto;

import com.worktracker.model.UserType;

public class UserRequestDTO {

    private String name;
    private String email;
    private UserType type;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
