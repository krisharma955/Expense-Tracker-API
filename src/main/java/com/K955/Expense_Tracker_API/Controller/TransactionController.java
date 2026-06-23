package com.K955.Expense_Tracker_API.Controller;

import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionRequest;
import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionResponse;
import com.K955.Expense_Tracker_API.DTOs.Transaction.UpdateTransactionRequest;
import com.K955.Expense_Tracker_API.Enum.Category;
import com.K955.Expense_Tracker_API.Enum.TransactionType;
import com.K955.Expense_Tracker_API.Security.JwtAuthUtil;
import com.K955.Expense_Tracker_API.Service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtAuthUtil jwtAuthUtil;

    @PostMapping
    @Operation(summary = "Create a New Transaction")
    public ResponseEntity<TransactionResponse> createNewTransaction(@Valid @RequestBody TransactionRequest request) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createNewTransaction(userId, request));
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Find Transaction By Id")
    public ResponseEntity<TransactionResponse> findTransactionById(@PathVariable Long transactionId) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(transactionService.getTransactionById(userId, transactionId));
    }

    @GetMapping
    @Operation(summary = "Filtering on Transactions")
    public ResponseEntity<Page<TransactionResponse>> getAllTransactionsPageSortSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false)Category category,
            @RequestParam(required = false)TransactionType type,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String dir
            ) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(transactionService
                .getAllTransactions(userId, keyword, category, type, month, year, page, size, sortBy, dir));
    }

    @PatchMapping("/{transactionId}")
    @Operation(summary = "Update Transaction By Id")
    public ResponseEntity<TransactionResponse> updateTransactionById(@PathVariable Long transactionId,
                                                                     @Valid @RequestBody UpdateTransactionRequest request) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(transactionService.updateTransactionById(userId, transactionId, request));
    }

    @DeleteMapping("/{transactionId}")
    @Operation(summary = "Delete Transaction By Id")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long transactionId) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        transactionService.deleteTransactionById(userId, transactionId);
        return ResponseEntity.noContent().build();
    }

}
