package com.K955.Expense_Tracker_API.Service.ImpL;

import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionRequest;
import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionResponse;
import com.K955.Expense_Tracker_API.DTOs.Transaction.UpdateTransactionRequest;
import com.K955.Expense_Tracker_API.Entity.Transaction;
import com.K955.Expense_Tracker_API.Entity.User;
import com.K955.Expense_Tracker_API.Enum.Category;
import com.K955.Expense_Tracker_API.Enum.TransactionType;
import com.K955.Expense_Tracker_API.Exception.BadRequestException;
import com.K955.Expense_Tracker_API.Exception.ResourceNotFoundException;
import com.K955.Expense_Tracker_API.Mapper.TransactionMapper;
import com.K955.Expense_Tracker_API.Repository.TransactionRepository;
import com.K955.Expense_Tracker_API.Repository.UserRepository;
import com.K955.Expense_Tracker_API.Service.TransactionService;
import com.K955.Expense_Tracker_API.Specifications.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpL implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponse createNewTransaction(Long userId, TransactionRequest request) {
        User user = getUser(userId);

        Transaction transaction = Transaction.builder()
                .title(request.title())
                .description(request.description())
                .user(user)
                .amount(request.amount())
                .date(request.date())
                .category(request.category())
                .type(request.type())
                .build();
        Transaction saved = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(saved);
    }

    @Override
    public TransactionResponse getTransactionById(Long userId, Long transactionId) {
        User user = getUser(userId);
        Transaction transaction = getTransaction(transactionId);

        if(!transaction.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Not Accessible");
        }

        return transactionMapper.toTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse updateTransactionById(Long userId, Long transactionId, UpdateTransactionRequest request) {
        User user = getUser(userId);
        Transaction transaction = getTransaction(transactionId);

        if(!transaction.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Not Accessible");
        }

        if(request.title() != null) transaction.setTitle(request.title());
        if(request.description() != null) transaction.setDescription(request.description());
        if(request.amount() != null) transaction.setAmount(request.amount());
        if(request.date() != null) transaction.setDate(request.date());
        if(request.category() != null) transaction.setCategory(request.category());
        if(request.type() != null) transaction.setType(request.type());

        Transaction saved = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(saved);
    }

    @Override
    public void deleteTransactionById(Long userId, Long transactionId) {
        User user = getUser(userId);
        Transaction transaction = getTransaction(transactionId);

        if(!transaction.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Not Accessible");
        }

        transaction.setDeletedAt(Instant.now());
        transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionResponse> getAllTransactions(Long userId, String keyword, Category category, TransactionType type, Integer month, Integer year, int page, int size, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Transaction> spec =
                Specification.where(
                        TransactionSpecification.hasUserId(userId)
                );

        if(category != null) {
            spec = spec.and(
                    TransactionSpecification.hasCategory(category)
            );
        }

        if(type != null) {
            spec = spec.and(
                    TransactionSpecification.hasType(type)
            );
        }

        if(keyword != null && !keyword.isBlank()) {
            spec = spec.and(
                    TransactionSpecification.titleContains(keyword)
            );
        }

        if(month != null && year != null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            spec = spec.and(
                    TransactionSpecification.betweenDates(startDate, endDate)
            );
        }

        Page<Transaction> transactions = transactionRepository.findAll(spec, pageable);
        return transactions.map(transactionMapper::toTransactionResponse);
    }

    /// Util Methods

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId.toString(), "User"));
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(transactionId.toString(), "Transaction"));
    }

}
