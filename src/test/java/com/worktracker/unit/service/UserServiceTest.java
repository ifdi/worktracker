package com.worktracker.unit.service;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.UpdatePasswordDTO;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.repository.UserRepository;
import com.worktracker.service.UserService;
import com.worktracker.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void deleteUserSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test(expected = WorktrackerException.class)
    public void deleteUserNonExist() {
        when(userRepository.existsById(2L)).thenReturn(false);

        userService.deleteUser(2L);
    }

    @Test
    public void getEmployeesSuccess() {
        User user = new User();
        user.setName("Test");
        when(userRepository.findAllByUserType(UserType.EMPLOYEE)).thenReturn(List.of(user));

        List<User> result = userService.getEmployees();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getName());
    }

    @Test
    public void getUserExist() {
        User user = new User();
        user.setName("Test");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @Test(expected = WorktrackerException.class)
    public void getUserNonExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.getUserById(1L);
        System.out.println();
    }

    @Test
    public void createUserSuccess() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test name");
        userRequestDTO.setEmail("Test email");
        userRequestDTO.setType(UserType.EMPLOYEE);

        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        User result = userService.createUser(userRequestDTO);

        assertNotNull(result);
        assertEquals("Test name", result.getName());
        assertEquals("Test email", result.getEmail());
        assertEquals(UserType.EMPLOYEE, result.getUserType());
    }

    @Test
    public void changePasswordSuccess() {
        Long id = 1L;
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setOldPassword("Old pass test".toCharArray());
        updatePasswordDTO.setNewPassword("new pass test".toCharArray());
        User user = new User();
        user.setPassword(updatePasswordDTO.getOldPassword());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).then(i -> i.getArguments()[0]);

        User result = userService.changePassword(id, updatePasswordDTO);

        verify(userRepository, times(1)).save(any());
        assertArrayEquals("new pass test".toCharArray(), result.getPassword());
    }

    @Test(expected = WorktrackerException.class)
    public void changePasswordWrongPassword() {
        Long id = 1L;
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setOldPassword("Old pass test".toCharArray());
        updatePasswordDTO.setNewPassword("new pass test".toCharArray());
        User user = new User();
        user.setPassword("wrong password".toCharArray());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.changePassword(id, updatePasswordDTO);

        verify(userService, times(1)).getUserById(id);
        verify(userRepository, times(0)).save(any());
        assertEquals("new pass test", Arrays.toString(result.getPassword()));
    }
}
