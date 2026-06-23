package com.K955.Expense_Tracker_API.Controller;

import com.K955.Expense_Tracker_API.DTOs.Summary.*;
import com.K955.Expense_Tracker_API.Security.JwtAuthUtil;
import com.K955.Expense_Tracker_API.Service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summary")
public class SummaryController {

    private final JwtAuthUtil jwtAuthUtil;
    private final SummaryService summaryService;

    @GetMapping
    @Operation(summary = "Overall Transaction Summary")
    public ResponseEntity<SummaryResponse> getOverallSummary() {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(summaryService.getOverallSummary(userId));
    }

    @GetMapping("/categories")
    @Operation(summary = "Category wise Transaction Summary")
    public ResponseEntity<List<CategorySummaryResponse>> getCategorySummary() {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(summaryService.getCategorySummary(userId));
    }

}
