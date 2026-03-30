package com.secureapp.controller;

import com.secureapp.service.CustomUserDetailsService;
import com.secureapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void register_existingUsername_showsGenericError() throws Exception {
        when(userService.usernameExists("existinguser")).thenReturn(true);

        mockMvc.perform(post("/register")
                .with(csrf())
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john@example.com")
                .param("username", "existinguser")
                .param("password", "Password1!")
                .param("confirmPassword", "Password1!"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registrationError"));
    }

    @Test
    void register_invalidPasswordFormat_failsValidation() throws Exception {
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john@example.com")
                .param("username", "newuser")
                .param("password", "weak")
                .param("confirmPassword", "weak"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("form", "password"));
    }

    @Test
    void register_validData_redirectsToLogin() throws Exception {
        when(userService.usernameExists("newuser")).thenReturn(false);
        when(userService.emailExists("john@example.com")).thenReturn(false);

        mockMvc.perform(post("/register")
                .with(csrf())
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john@example.com")
                .param("username", "newuser")
                .param("password", "Password1!secure")
                .param("confirmPassword", "Password1!secure"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));
    }
}
