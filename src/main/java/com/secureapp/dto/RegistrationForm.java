package com.secureapp.dto;

import com.secureapp.util.ValidationConstants;
import jakarta.validation.constraints.*;

public class RegistrationForm {

    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^[a-zA-Z]{2,50}$", message = "First name must be 2-50 letters only.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^[a-zA-Z]{2,50}$", message = "Last name must be 2-50 letters only.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter a valid email address.")
    @Size(max = 100, message = "Email must be at most 100 characters.")
    private String email;

    @NotBlank(message = "Username is required.")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,50}$", message = "Username must be 3-50 characters: letters, digits, or underscores.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 12, max = 128, message = "Password must be 12-128 characters.")
    @Pattern(
        regexp = ValidationConstants.PASSWORD_REGEX,
        message = ValidationConstants.PASSWORD_MESSAGE
    )
    private String password;

    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
