package com.secureapp.controller;

import com.secureapp.service.FeedbackService;
import com.secureapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final FeedbackService feedbackService;
    private final UserService userService;

    public AdminController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("feedbackCount", feedbackService.getAllFeedback().size());
        model.addAttribute("userCount", userService.getAllUsers().size());
        return "admin-dashboard";
    }

    @GetMapping("/feedback")
    public String adminFeedback(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        return "admin-feedback";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Principal principal) {
        try {
            userService.deleteUserByAdmin(id, principal.getName());
            logger.info("Admin " + principal.getName() + " deleted user with id " + id);
        } catch (IllegalStateException e) {
            return "redirect:/admin/users?error";
        }
        return "redirect:/admin/users?deleted";
    }
}
