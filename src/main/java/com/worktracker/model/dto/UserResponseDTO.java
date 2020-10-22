package com.worktracker.model.dto;

import com.worktracker.model.TypeUser;
import com.worktracker.model.User;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private TypeUser type;

    public UserResponseDTO(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setType(user.getTypeUser());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypeUser getType() {
        return type;
    }

    public void setType(TypeUser type) {
        this.type = type;
    }
}
