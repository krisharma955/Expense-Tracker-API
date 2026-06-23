package com.K955.Expense_Tracker_API.DTOs.Summary;

import java.math.BigDecimal;

public record SummaryResponse(
        BigDecimal totalIncome,
        BigDecimal expense,
        BigDecimal balance
) {
}
