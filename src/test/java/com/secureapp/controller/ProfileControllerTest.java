package com.secureapp.controller;

import com.secureapp.config.SecurityConfig;
import com.secureapp.service.CustomUserDetailsService;
import com.secureapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ProfileController.class)
@Import(SecurityConfig.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "testuser")
    void changePassword_wrongCurrentPassword_showsError() throws Exception {
        when(userService.changePassword("testuser", "wrongpass", "NewPassword1!")).thenReturn(false);

        mockMvc.perform(post("/change-password")
                .with(csrf())
                .param("currentPassword", "wrongpass")
                .param("newPassword", "NewPassword1!")
                .param("confirmNewPassword", "NewPassword1!"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void changePassword_validData_redirectsToHome() throws Exception {
        when(userService.changePassword("testuser", "OldPassword1!", "NewPassword1!")).thenReturn(true);

        mockMvc.perform(post("/change-password")
                .with(csrf())
                .param("currentPassword", "OldPassword1!")
                .param("newPassword", "NewPassword1!")
                .param("confirmNewPassword", "NewPassword1!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home?passwordChanged"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void logout_invalidatesSession_redirectsToLogin() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    void sessionTimeout_unauthenticatedRequest_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/change-password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}
