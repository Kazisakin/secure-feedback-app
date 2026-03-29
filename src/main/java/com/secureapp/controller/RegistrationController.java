package com.secureapp.controller;

import com.secureapp.dto.RegistrationForm;
import com.secureapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("form") RegistrationForm form,
                           BindingResult result, Model model) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Passwords do not match.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        if (userService.usernameExists(form.getUsername()) || userService.emailExists(form.getEmail())) {
            model.addAttribute("registrationError", "Registration could not be completed. Please try again.");
            return "register";
        }

        userService.register(form);
        return "redirect:/login?registered";
    }
}
