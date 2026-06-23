package com.K955.Expense_Tracker_API.Service.ImpL;

import com.K955.Expense_Tracker_API.DTOs.Summary.CategorySummaryResponse;
import com.K955.Expense_Tracker_API.DTOs.Summary.SummaryResponse;
import com.K955.Expense_Tracker_API.Enum.TransactionType;
import com.K955.Expense_Tracker_API.Repository.TransactionRepository;
import com.K955.Expense_Tracker_API.Service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpL implements SummaryService {

    private final TransactionRepository transactionRepository;

    @Override
    public SummaryResponse getOverallSummary(Long userId) {
        BigDecimal income = Optional.ofNullable(
                transactionRepository.getTotalByType(userId, TransactionType.INCOME))
                .orElse(BigDecimal.ZERO);

        BigDecimal expense = Optional.ofNullable(
                transactionRepository.getTotalByType(userId, TransactionType.EXPENSE))
                .orElse(BigDecimal.ZERO);

        BigDecimal balance = income.subtract(expense);

        return new SummaryResponse(income, expense, balance);
    }

    @Override
    public List<CategorySummaryResponse> getCategorySummary(Long userId) {
        return transactionRepository.getCategorySummary(userId);
    }

}
