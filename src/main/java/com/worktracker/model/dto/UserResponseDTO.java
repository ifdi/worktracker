package com.worktracker.model.dto;

import com.worktracker.model.TypeUser;
import com.worktracker.model.User;

public class UserResponseDTO {

    private final Long id;
    private final String name;
    private final String email;
    private final TypeUser type;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.type = user.getTypeUser();
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

    public TypeUser getType() {
        return type;
    }
}
