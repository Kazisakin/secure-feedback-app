package com.secureapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    /**
     * Display the feedback form page.
     * - Requires authenticated user (enforced by SecurityConfig)
     * - Return the feedback template
     */
    @GetMapping
    public String feedbackPage() {
        return "feedback";
    }

    /**
     * Handle feedback form submission.
     * - Validate input fields (name, email, message)
     * - Save feedback to the database linked to the logged-in user
     * - Redirect back with a success or error message
     */
    @PostMapping
    public String submitFeedback() {
        return "feedback";
    }
}
