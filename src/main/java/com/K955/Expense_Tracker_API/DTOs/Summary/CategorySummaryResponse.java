package com.K955.Expense_Tracker_API.DTOs.Summary;

import com.K955.Expense_Tracker_API.Enum.Category;

import java.math.BigDecimal;

public record CategorySummaryResponse(
        Category category,
        BigDecimal amount
) {
}
