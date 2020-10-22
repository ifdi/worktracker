package com.worktracker.model.dto;

import com.worktracker.model.TypeUser;

public class UserRequestDTO {

    private String name;
    private String email;
    private TypeUser type;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public TypeUser getType() {
        return type;
    }
}
