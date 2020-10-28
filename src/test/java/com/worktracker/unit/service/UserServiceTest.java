package com.worktracker.unit.service;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.User;
import com.worktracker.model.UserType;
import com.worktracker.model.dto.UserRequestDTO;
import com.worktracker.repository.UserRepository;
import com.worktracker.service.UserService;
import com.worktracker.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        //when
        userService.deleteUser(1L);

        //then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getEmployeesSuccess() {
        //given
        User user = new User();
        user.setName("Test");
        when(userRepository.findAllByUserType(UserType.EMPLOYEE)).thenReturn(List.of(user));

        //when
        List<User> result = userService.getEmployees();

        //then
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getName());
    }

    @Test
    public void getUserExist() {
        //given
        User user = new User();
        user.setName("Test");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        User result = userService.getUser(1L);

        //then
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @Test(expected = WorktrackerException.class)
    public void getUserNonExist() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        userService.getUser(1L);
    }

    @Test
    public void createUserSuccess() {
        //given
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test name");
        userRequestDTO.setEmail("Test email");
        userRequestDTO.setType(UserType.EMPLOYEE);

        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        User result = userService.createUser(userRequestDTO);

        //then
        assertNotNull(result);
        assertEquals("Test name", result.getName());
        assertEquals("Test email", result.getEmail());
        assertEquals(UserType.EMPLOYEE, result.getUserType());
    }
}
