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
    @GetMapping("/users")
public String listUsers(Model model) {
    model.addAttribute("users", userService.getAllUsers());
    return "admin-users";
}

@GetMapping("/users/add")
public String showAddUserForm(Model model) {
    model.addAttribute("form", new AdminUserForm());
    return "admin-user-form";
}

@PostMapping("/users/add")
public String addUser(@Valid @ModelAttribute("form") AdminUserForm form,
                      BindingResult result,
                      Model model) {
    if (result.hasErrors()) {
        return "admin-user-form";
    }

    if (userService.usernameExists(form.getUsername()) || userService.emailExists(form.getEmail())) {
        model.addAttribute("error", "Username or email already exists.");
        return "admin-user-form";
    }

    userService.createUserByAdmin(form);
    return "redirect:/admin/users?added";
}

@GetMapping("/users/edit/{id}")
public String showEditUserForm(@PathVariable Long id, Model model) {
    model.addAttribute("form", userService.getUserForEdit(id));
    model.addAttribute("userId", id);
    return "admin-user-edit";
}

@PostMapping("/users/edit/{id}")
public String editUser(@PathVariable Long id,
                       @Valid @ModelAttribute("form") AdminUserForm form,
                       BindingResult result,
                       Model model) {
    if (result.hasErrors()) {
        model.addAttribute("userId", id);
        return "admin-user-edit";
    }

    userService.updateUserByAdmin(id, form);
    return "redirect:/admin/users?updated";
}

@PostMapping("/users/delete/{id}")
public String deleteUser(@PathVariable Long id, Principal principal) {
    userService.deleteUserByAdmin(id, principal.getName());
    return "redirect:/admin/users?deleted";
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
