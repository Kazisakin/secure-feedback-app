package com.secureapp.controller;

import com.secureapp.dto.ChangePasswordForm;
import com.secureapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/change-password")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("form", new ChangePasswordForm());
        return "change-password";
    }

    @PostMapping
    public String changePassword(@Valid @ModelAttribute("form") ChangePasswordForm form,
                                 BindingResult result, Model model, Principal principal) {

        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "mismatch", "Passwords do not match.");
        }

        if (result.hasErrors()) {
            return "change-password";
        }

        boolean success = userService.changePassword(principal.getName(),
                form.getCurrentPassword(), form.getNewPassword());

        if (!success) {
            model.addAttribute("error", "Current password is incorrect.");
            return "change-password";
        }

        return "redirect:/home?passwordChanged";
    }
}
