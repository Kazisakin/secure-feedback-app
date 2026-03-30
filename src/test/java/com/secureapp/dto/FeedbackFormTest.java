package com.secureapp.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackFormTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void emptyName_failsNotBlank() {
        FeedbackForm form = new FeedbackForm();
        form.setName("");
        form.setEmail("test@test.com");
        form.setMessage("This is a valid message.");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void invalidEmail_failsEmailConstraint() {
        FeedbackForm form = new FeedbackForm();
        form.setName("Apoorva");
        form.setEmail("notanemail");
        form.setMessage("This is a valid message.");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void shortMessage_failsSizeConstraint() {
        FeedbackForm form = new FeedbackForm();
        form.setName("Apoorva");
        form.setEmail("test@test.com");
        form.setMessage("short");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("message")));
    }

    @Test
    void validForm_passesAllConstraints() {
        FeedbackForm form = new FeedbackForm();
        form.setName("Apoorva");
        form.setEmail("test@test.com");
        form.setMessage("This is a perfectly valid feedback message.");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }
}