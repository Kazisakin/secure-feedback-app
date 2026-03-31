package com.secureapp.controller;

import com.secureapp.dto.ChangePasswordForm;
import com.secureapp.dto.UpdateProfileForm;
import com.secureapp.entity.User;
import com.secureapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        UpdateProfileForm form = new UpdateProfileForm();
        form.setFirstName(user.getFirstName());
        form.setLastName(user.getLastName());
        form.setEmail(user.getEmail());
        model.addAttribute("form", form);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("createdAt", user.getCreatedAt());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("form") UpdateProfileForm form,
                                BindingResult result, Model model, Principal principal) {

        if (result.hasErrors()) {
            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("createdAt", user.getCreatedAt());
            return "profile";
        }

        String updateResult = userService.updateProfile(principal.getName(), form);

        if (updateResult.equals("email_taken")) {
            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("createdAt", user.getCreatedAt());
            model.addAttribute("error", "That email is already in use by another account.");
            return "profile";
        }

        if (updateResult.equals("no_changes")) {
            return "redirect:/profile?nochanges";
        }

        return "redirect:/profile?updated";
    }

    @GetMapping("/change-password")
    public String showPasswordForm(Model model) {
        model.addAttribute("form", new ChangePasswordForm());
        return "change-password";
    }

    @PostMapping("/change-password")
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
