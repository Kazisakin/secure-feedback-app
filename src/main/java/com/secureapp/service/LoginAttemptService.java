package com.secureapp.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginAttemptService {

    private static final int MAX_ENTRIES = 1000;

    private Map<String, Integer> failedAttempts = new HashMap<>();
    private Map<String, LocalDateTime> lockoutTime = new HashMap<>();

    public void loginFailed(String username) {
        if (failedAttempts.size() >= MAX_ENTRIES) {
            failedAttempts.clear();
            lockoutTime.clear();
        }

        int attempts = 0;

        if (failedAttempts.containsKey(username)) {
            attempts = failedAttempts.get(username);
        }

        attempts = attempts + 1;
        failedAttempts.put(username, attempts);

        if (attempts >= 5) {
            lockoutTime.put(username, LocalDateTime.now());
        }
    }

    public void loginSucceeded(String username) {
        failedAttempts.remove(username);
        lockoutTime.remove(username);
    }

    public boolean isLocked(String username) {
        if (!lockoutTime.containsKey(username)) {
            return false;
        }

        LocalDateTime lockedAt = lockoutTime.get(username);
        LocalDateTime unlockAt = lockedAt.plusMinutes(15);

        if (LocalDateTime.now().isAfter(unlockAt)) {
            failedAttempts.remove(username);
            lockoutTime.remove(username);
            return false;
        }

        return true;
    }
}
