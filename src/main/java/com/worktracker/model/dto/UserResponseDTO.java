package com.worktracker.model.dto;

import com.worktracker.model.TypeUser;
import com.worktracker.model.User;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private TypeUser type;

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

    public UserResponseDTO(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setType(user.getTypeUser());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(TypeUser type) {
        this.type = type;
    }

}
