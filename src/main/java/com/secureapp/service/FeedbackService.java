package com.secureapp.service;

import com.secureapp.dto.FeedbackForm;
import com.secureapp.entity.Feedback;
import com.secureapp.entity.User;
import com.secureapp.repository.FeedbackRepository;
import com.secureapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public void saveFeedback(FeedbackForm form, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Feedback feedback = new Feedback();
        feedback.setName(form.getName());
        feedback.setEmail(form.getEmail());
        feedback.setMessage(form.getMessage());
        feedback.setUser(user);

        feedbackRepository.save(feedback);
    }
    public List<Feedback> getAllFeedback() {
    return feedbackRepository.findAll();
  }
}
