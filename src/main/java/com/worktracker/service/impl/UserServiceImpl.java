package com.worktracker.service.impl;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.UpdatePasswordDTO;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.repository.UserRepository;
import com.worktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_PASSWORD = "pass24ange";
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getEmail() == null || userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new WorktrackerException("Email is not provided or is invalid.");
        }

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserType(userRequestDTO.getType());
        user.setPassword(DEFAULT_PASSWORD.toCharArray());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null || !userRepository.existsById(id)) {
            throw new WorktrackerException("User does not exist.");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getEmployees() {
        return userRepository.findAllByUserType(UserType.EMPLOYEE);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new WorktrackerException("User not found"));
    }

    @Override
    public User getUserByEmailAndPass(String email, char[] password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new WorktrackerException("User not found"));
    }

    @Override
    public User changePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        User user = this.getUserById(id);
        if (!Arrays.equals(user.getPassword(), updatePasswordDTO.getOldPassword())) {
            throw new WorktrackerException("Invalid Password");
        }
        user.setPassword(updatePasswordDTO.getNewPassword());

        return userRepository.save(user);
    }
}
