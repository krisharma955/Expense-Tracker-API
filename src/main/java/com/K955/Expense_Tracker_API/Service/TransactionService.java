package com.K955.Expense_Tracker_API.Service;

import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionRequest;
import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionResponse;
import com.K955.Expense_Tracker_API.DTOs.Transaction.UpdateTransactionRequest;
import com.K955.Expense_Tracker_API.Enum.Category;
import com.K955.Expense_Tracker_API.Enum.TransactionType;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransactionResponse createNewTransaction(Long userId, @Valid TransactionRequest request);

    TransactionResponse getTransactionById(Long userId, Long transactionId);

    TransactionResponse updateTransactionById(Long userId, Long transactionId, @Valid UpdateTransactionRequest request);

    void deleteTransactionById(Long userId, Long transactionId);

    Page<TransactionResponse> getAllTransactions(Long userId, String keyword, Category category, TransactionType type, Integer month, Integer year, int page, int size, String sortBy, String dir);
}
