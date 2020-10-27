package com.worktracker.model.dto;

import com.worktracker.model.User;
import com.worktracker.model.UserType;

public class UserResponseDTO {

    private final Long id;
    private final String name;
    private final String email;
    private final UserType type;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.type = user.getUserType();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }
}
