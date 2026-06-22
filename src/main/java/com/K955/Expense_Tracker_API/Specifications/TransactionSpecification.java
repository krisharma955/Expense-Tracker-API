package com.K955.Expense_Tracker_API.Specifications;

import com.K955.Expense_Tracker_API.Entity.Transaction;
import com.K955.Expense_Tracker_API.Enum.Category;
import com.K955.Expense_Tracker_API.Enum.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransactionSpecification {

    public static Specification<Transaction> hasUserId(Long userId) {
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("user").get("id"), userId
                    )
                );
    }

    public static Specification<Transaction> hasCategory(Category category) {
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("category"), category
                    )
                );
    }

    public static Specification<Transaction> hasType(TransactionType type) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("type"), type
                )
        );
    }

    public static Specification<Transaction> titleContains(String keyword) {
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("title")
                            ),
                            "%" +keyword.toLowerCase()+ "%"
                    )
                );
    }

    public static Specification<Transaction> betweenDates(LocalDate startDate, LocalDate endDate) {
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(
                            root.get("date"), startDate, endDate
                    )
                );
    }

}
