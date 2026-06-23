package com.K955.Expense_Tracker_API.Repository;

import com.K955.Expense_Tracker_API.DTOs.Summary.CategorySummaryResponse;
import com.K955.Expense_Tracker_API.Entity.Transaction;
import com.K955.Expense_Tracker_API.Enum.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("""
            SELECT SUM(amount)
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.type = :type
            """)
    BigDecimal getTotalByType(@Param("userId") Long userId, @Param("type") TransactionType type);

    @Query("""
            SELECT new com.K955.Expense_Tracker_API.DTOs.Summary.CategorySummaryResponse(
                t.category, SUM(t.amount)
            )
            FROM Transaction t
            WHERE t.user.id = :userId
            GROUP BY t.category
            """)
    List<CategorySummaryResponse> getCategorySummary(@Param("userId") Long userId);

}
