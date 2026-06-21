package com.K955.Expense_Tracker_API.Repository;

import com.K955.Expense_Tracker_API.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.deletedAt IS NULL
            AND t.user.id = :userId
            ORDER BY t.updatedAt DESC
            """)
    List<Transaction> findAllAccessibleTransactions(@RequestParam("userId") Long userId);

}
