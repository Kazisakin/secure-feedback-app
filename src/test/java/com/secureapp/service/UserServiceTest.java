package com.secureapp.service;

import com.secureapp.entity.User;
import com.secureapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void changePassword_usernameNotFound_returnsFalse() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        boolean result = userService.changePassword("ghost", "any", "newPass");
        assertFalse(result);
    }

    @Test
    void changePassword_wrongCurrentPassword_returnsFalse() {
        User user = new User();
        user.setPassword("hashed");
        when(userRepository.findByUsername("apoorva")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "hashed")).thenReturn(false);

        boolean result = userService.changePassword("apoorva", "wrongpass", "newPass");
        assertFalse(result);
    }

    @Test
    void changePassword_correctCredentials_returnsTrueAndUpdates() {
        User user = new User();
        user.setPassword("hashed");
        when(userRepository.findByUsername("apoorva")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("correctpass", "hashed")).thenReturn(true);
        when(passwordEncoder.encode("newPass123!")).thenReturn("newHashed");

        boolean result = userService.changePassword("apoorva", "correctpass", "newPass123!");
        assertTrue(result);
        verify(userRepository).save(user);
    }

    @Test
    void usernameExists_returnsTrue_whenFound() {
        when(userRepository.existsByUsername("apoorva")).thenReturn(true);
        assertTrue(userService.usernameExists("apoorva"));
    }

    @Test
    void emailExists_returnsFalse_whenNotFound() {
        when(userRepository.existsByEmail("nobody@test.com")).thenReturn(false);
        assertFalse(userService.emailExists("nobody@test.com"));
    }
}