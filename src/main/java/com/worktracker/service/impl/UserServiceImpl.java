package com.worktracker.service.impl;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.repository.UserRepository;
import com.worktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_PASSWORD = "pass24ange";
    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserType(userRequestDTO.getType());
        user.setPassword(DEFAULT_PASSWORD);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getEmployees() {
        return userRepository.findAllByUserType(UserType.EMPLOYEE);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new WorktrackerException("User not found"));
    }
}
