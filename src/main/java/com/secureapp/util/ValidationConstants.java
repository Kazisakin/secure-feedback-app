package com.secureapp.util;

public class ValidationConstants {

    public static final String PASSWORD_REGEX =
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{12,128}$";

    public static final String PASSWORD_MESSAGE =
        "Password must contain uppercase, lowercase, digit, and special character.";
}
