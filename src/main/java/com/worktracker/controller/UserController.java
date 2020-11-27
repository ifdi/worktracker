package com.worktracker.controller;

import com.worktracker.model.User;
import com.worktracker.model.dto.UpdatePasswordDTO;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.model.dto.UserResponseDTO;
import com.worktracker.service.AuthorizationService;
import com.worktracker.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "User Controller")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public UserController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO,
                                      @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
        authorizationService.validateTokenAuthorization(token);
        User user = userService.createUser(userRequestDTO);

        return new UserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id,
                           @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
        authorizationService.validateTokenAuthorization(token);
        userService.deleteUser(id);
    }

    @GetMapping("/employees")
    public List<UserResponseDTO> getEmployees(@RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);

        return userService.getEmployees().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/password")
    public void changePassword(@PathVariable long id,
                               @RequestBody UpdatePasswordDTO updatePasswordDTO,
                               @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
        userService.changePassword(id, updatePasswordDTO);
    }
}
