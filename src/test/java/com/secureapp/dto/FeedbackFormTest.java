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
    void blankMessage_failsNotBlank() {
        FeedbackForm form = new FeedbackForm();
        form.setMessage("");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("message")));
    }

    @Test
    void shortMessage_failsSizeConstraint() {
        FeedbackForm form = new FeedbackForm();
        form.setMessage("tooshort");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("message")));
    }

    @Test
    void tooLongMessage_failsSizeConstraint() {
        FeedbackForm form = new FeedbackForm();
        form.setMessage("a".repeat(1001));

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("message")));
    }

    @Test
    void validMessage_passesAllConstraints() {
        FeedbackForm form = new FeedbackForm();
        form.setMessage("This is a perfectly valid feedback message.");

        Set<ConstraintViolation<FeedbackForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }
}
