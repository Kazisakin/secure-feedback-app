package com.secureapp.repository;

import com.secureapp.entity.Feedback;
import com.secureapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUser(User user);
}
