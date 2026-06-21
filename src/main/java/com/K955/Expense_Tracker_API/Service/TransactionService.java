package com.K955.Expense_Tracker_API.Service;

import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionRequest;
import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionResponse;
import com.K955.Expense_Tracker_API.DTOs.Transaction.UpdateTransactionRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface TransactionService {
    TransactionResponse createNewTransaction(Long userId, @Valid TransactionRequest request);

    TransactionResponse getTransactionById(Long userId, Long transactionId);

    List<TransactionResponse> getAllTransactions(Long userId);

    TransactionResponse updateTransactionById(Long userId, Long transactionId, @Valid UpdateTransactionRequest request);

    void deleteTransactionById(Long userId, Long transactionId);
}
