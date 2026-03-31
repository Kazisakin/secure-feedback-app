package com.secureapp.controller;

import com.secureapp.config.SecurityConfig;
import com.secureapp.service.CustomUserDetailsService;
import com.secureapp.service.FeedbackService;
import com.secureapp.service.GuestRateLimitService;
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

@WebMvcTest(FeedbackController.class)
@Import(SecurityConfig.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private GuestRateLimitService guestRateLimitService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void getFeedback_unauthenticated_returns200() throws Exception {
        mockMvc.perform(get("/feedback"))
               .andExpect(status().isOk())
               .andExpect(view().name("feedback"));
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
    void postFeedback_emptyMessage_returnsFormWithErrors() throws Exception {
        mockMvc.perform(post("/feedback")
               .with(csrf())
               .param("message", ""))
               .andExpect(status().isOk())
               .andExpect(view().name("feedback"))
               .andExpect(model().attributeHasFieldErrors("feedbackForm", "message"));
    }

    @Test
    @WithMockUser(username = "apoorva")
    void postFeedback_validInput_redirects() throws Exception {
        mockMvc.perform(post("/feedback")
               .with(csrf())
               .param("message", "This is a valid feedback message that is long enough."))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/feedback?submitted"));
    }

    @Test
    @WithMockUser(username = "apoorva")
    void postFeedback_withoutCsrfToken_returns403() throws Exception {
        mockMvc.perform(post("/feedback")
               .param("message", "Valid message here that is long enough."))
               .andExpect(status().isForbidden());
    }
}
