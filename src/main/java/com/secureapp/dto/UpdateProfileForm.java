package com.secureapp.dto;

import jakarta.validation.constraints.*;

public class UpdateProfileForm {

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

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
