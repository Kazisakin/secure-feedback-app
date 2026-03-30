package com.secureapp.controller;

import com.secureapp.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Test
    void getFeedback_unauthenticated_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/feedback"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "apoorva")
    void getFeedback_authenticated_returns200() throws Exception {
        mockMvc.perform(get("/feedback"))
               .andExpect(status().isOk())
               .andExpect(view().name("feedback"));
    }

    @Test
    @WithMockUser(username = "apoorva")
    void postFeedback_emptyFields_returnsFormWithErrors() throws Exception {
        mockMvc.perform(post("/feedback")
               .with(csrf())
               .param("name", "")
               .param("email", "")
               .param("message", ""))
               .andExpect(status().isOk())
               .andExpect(view().name("feedback"))
               .andExpect(model().attributeHasFieldErrors("feedbackForm", "name", "email", "message"));
    }

    @Test
    @WithMockUser(username = "apoorva")
    void postFeedback_validInput_redirects() throws Exception {
        mockMvc.perform(post("/feedback")
               .with(csrf())
               .param("name", "Apoorva")
               .param("email", "test@test.com")
               .param("message", "This is a valid feedback message that is long enough."))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/feedback?submitted"));
    }

    @Test
    @WithMockUser(username = "apoorva")
    void postFeedback_withoutCsrfToken_returns403() throws Exception {
        mockMvc.perform(post("/feedback")
               .param("name", "Apoorva")
               .param("email", "test@test.com")
               .param("message", "Valid message here that is long enough."))
               .andExpect(status().isForbidden());
    }
}