package com.K955.Expense_Tracker_API.DTOs.Transaction;

import com.K955.Expense_Tracker_API.DTOs.User.UserProfileResponse;
import com.K955.Expense_Tracker_API.Enum.Category;
import com.K955.Expense_Tracker_API.Enum.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        String title,
        BigDecimal amount,
        Category category,
        TransactionType type,
        LocalDate date,
        UserProfileResponse user

) {
}
