package com.K955.Expense_Tracker_API.Service;

import com.K955.Expense_Tracker_API.DTOs.Summary.CategorySummaryResponse;
import com.K955.Expense_Tracker_API.DTOs.Summary.SummaryResponse;

import java.util.List;

public interface SummaryService {
    SummaryResponse getOverallSummary(Long userId);

    List<CategorySummaryResponse> getCategorySummary(Long userId);

}
