package com.secureapp.controller;

import com.secureapp.dto.FeedbackForm;
import com.secureapp.service.FeedbackService;
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

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String feedbackPage(Model model) {
        model.addAttribute("feedbackForm", new FeedbackForm());
        return "feedback";
    }

    @PostMapping
    public String submitFeedback(
            @Valid @ModelAttribute("feedbackForm") FeedbackForm form,
            BindingResult result,
            Principal principal,
            Model model) {

        if (result.hasErrors()) {
            return "feedback";
        }

        feedbackService.saveFeedback(form, principal.getName());
        return "redirect:/feedback?submitted";
    }
}
