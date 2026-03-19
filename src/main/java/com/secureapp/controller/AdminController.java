package com.secureapp.controller;

import com.secureapp.repository.UserRepository;
import com.secureapp.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FeedbackService feedbackService;
    private final UserRepository userRepository;

    public AdminController(FeedbackService feedbackService, UserRepository userRepository) {
        this.feedbackService = feedbackService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("feedbackCount", feedbackService.getAllFeedback().size());
        model.addAttribute("userCount", userRepository.count());
        return "admin-dashboard";
    }

    @GetMapping("/feedback")
    public String adminFeedback(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        return "admin-feedback";
    }
}
