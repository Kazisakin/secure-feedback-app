package com.secureapp.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class GuestRateLimitService {

    private static final int MAX_SUBMISSIONS = 3;
    private static final int WINDOW_HOURS = 1;
    private static final int MAX_ENTRIES = 1000;

    private Map<String, Integer> submissionCount = new HashMap<>();
    private Map<String, LocalDateTime> windowStart = new HashMap<>();

    public boolean isAllowed(String ipAddress) {
        if (submissionCount.size() >= MAX_ENTRIES) {
            submissionCount.clear();
            windowStart.clear();
        }

        if (!windowStart.containsKey(ipAddress)) {
            return true;
        }

        LocalDateTime windowExpiry = windowStart.get(ipAddress).plusHours(WINDOW_HOURS);

        if (LocalDateTime.now().isAfter(windowExpiry)) {
            submissionCount.remove(ipAddress);
            windowStart.remove(ipAddress);
            return true;
        }

        return submissionCount.get(ipAddress) < MAX_SUBMISSIONS;
    }

    public void recordSubmission(String ipAddress) {
        if (!windowStart.containsKey(ipAddress)) {
            windowStart.put(ipAddress, LocalDateTime.now());
            submissionCount.put(ipAddress, 1);
        } else {
            submissionCount.put(ipAddress, submissionCount.get(ipAddress) + 1);
        }
    }
}
