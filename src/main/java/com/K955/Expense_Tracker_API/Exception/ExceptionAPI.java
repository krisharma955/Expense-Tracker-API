package com.K955.Expense_Tracker_API.Exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ExceptionAPI(
        HttpStatus status,
        String message,
        Instant timestamp
) {
}
