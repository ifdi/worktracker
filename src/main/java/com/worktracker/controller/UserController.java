package com.worktracker.controller;

import com.worktracker.model.User;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.model.dto.UserResponseDTO;
import com.worktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.createUser(userRequestDTO);

        return new UserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/employees")
    public List<User> getEmployees() {
        return userService.getEmployees();
    }

}
