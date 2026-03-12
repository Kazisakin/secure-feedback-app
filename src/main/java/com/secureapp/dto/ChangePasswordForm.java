package com.secureapp.dto;

import com.secureapp.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordForm {

    @NotBlank(message = "Current password is required.")
    private String currentPassword;

    @NotBlank(message = "New password is required.")
    @Size(min = 12, max = 128, message = "Password must be 12-128 characters.")
    @Pattern(
        regexp = ValidationConstants.PASSWORD_REGEX,
        message = ValidationConstants.PASSWORD_MESSAGE
    )
    private String newPassword;

    @NotBlank(message = "Please confirm your new password.")
    private String confirmNewPassword;

    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getConfirmNewPassword() { return confirmNewPassword; }
    public void setConfirmNewPassword(String confirmNewPassword) { this.confirmNewPassword = confirmNewPassword; }
}
