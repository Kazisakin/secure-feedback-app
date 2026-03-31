package com.secureapp.controller;

import com.secureapp.dto.FeedbackForm;
import com.secureapp.service.FeedbackService;
import com.secureapp.service.GuestRateLimitService;
import com.secureapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final GuestRateLimitService guestRateLimitService;
    private final UserService userService;

    public FeedbackController(FeedbackService feedbackService, GuestRateLimitService guestRateLimitService, UserService userService) {
        this.feedbackService = feedbackService;
        this.guestRateLimitService = guestRateLimitService;
        this.userService = userService;
    }

    private boolean isAdmin(Principal principal) {
        if (principal == null) return false;
        return "ADMIN".equals(userService.getUserByUsername(principal.getName()).getRole());
    }

    @GetMapping
    public String feedbackPage(Model model, Principal principal) {
        if (isAdmin(principal)) {
            model.addAttribute("adminError", true);
            return "feedback";
        }
        model.addAttribute("feedbackForm", new FeedbackForm());
        model.addAttribute("isGuest", principal == null);
        return "feedback";
    }

    @PostMapping
    public String submitFeedback(
            @Valid @ModelAttribute("feedbackForm") FeedbackForm form,
            BindingResult result,
            Principal principal,
            HttpServletRequest request,
            Model model) {

        if (isAdmin(principal)) {
            model.addAttribute("adminError", true);
            return "feedback";
        }

        boolean isGuest = (principal == null);

        if (isGuest) {
            String ip = request.getRemoteAddr();

            if (!guestRateLimitService.isAllowed(ip)) {
                model.addAttribute("rateLimitError", "You have reached the maximum of 3 submissions per hour. Please try again later.");
                model.addAttribute("isGuest", true);
                return "feedback";
            }

            if (form.getName() == null || form.getName().isBlank()) {
                result.rejectValue("name", "required", "Name is required.");
            }

            if (form.getEmail() == null || form.getEmail().isBlank()) {
                result.rejectValue("email", "required", "Email is required.");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("isGuest", isGuest);
            return "feedback";
        }

        if (isGuest) {
            guestRateLimitService.recordSubmission(request.getRemoteAddr());
            feedbackService.saveFeedbackForGuest(form);
        } else {
            feedbackService.saveFeedbackForUser(form, principal.getName());
        }

        return "redirect:/feedback?submitted";
    }
}
