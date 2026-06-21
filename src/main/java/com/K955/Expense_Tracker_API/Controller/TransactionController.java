package com.K955.Expense_Tracker_API.Controller;

import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionRequest;
import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionResponse;
import com.K955.Expense_Tracker_API.DTOs.Transaction.UpdateTransactionRequest;
import com.K955.Expense_Tracker_API.Security.JwtAuthUtil;
import com.K955.Expense_Tracker_API.Service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<TransactionResponse> createNewTransaction(@Valid @RequestBody TransactionRequest request) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createNewTransaction(userId, request));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> findTransactionById(@PathVariable Long transactionId) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(transactionService.getTransactionById(userId, transactionId));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(transactionService.getAllTransactions(userId));
    }

    @PatchMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> updateTransactionById(@PathVariable Long transactionId,
                                                                     @Valid @RequestBody UpdateTransactionRequest request) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(transactionService.updateTransactionById(userId, transactionId, request));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long transactionId) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        transactionService.deleteTransactionById(userId, transactionId);
        return ResponseEntity.noContent().build();
    }

}
