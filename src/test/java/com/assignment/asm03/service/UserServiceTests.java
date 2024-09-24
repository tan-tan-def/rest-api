package com.assignment.asm03.service;

import com.assignment.asm03.entity.User;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
    }

    @Test
    public void FindByEmail_ExistingEmail_ReturnUser() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User foundUser = userService.findByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void FindByEmail_NonExistingEmail_ThrowNotFoundException() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.findByEmail("nonexistent@example.com");
        });

        assertEquals("Không tìm thấy người dùng với email: nonexistent@example.com", exception.getMessage());
    }

    @Test
    public void UserService_Save_ReturnSavedUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
    }

    @Test
    public void FindById_ExistingId_ReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void FindById_NonExistingId_ThrowNotFoundException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.findById(99);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void DeleteById_CallDeleteByIdOnRepository() {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    public void FindAll_ReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
    }
}
