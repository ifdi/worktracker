package com.worktracker.service;

import com.worktracker.model.User;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.model.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    User createUser(UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

    List<User> getEmployees();

}
