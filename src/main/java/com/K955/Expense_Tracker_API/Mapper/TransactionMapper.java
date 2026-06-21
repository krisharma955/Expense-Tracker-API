package com.K955.Expense_Tracker_API.Mapper;

import com.K955.Expense_Tracker_API.DTOs.Transaction.TransactionResponse;
import com.K955.Expense_Tracker_API.Entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "user.id", target = "user.id")
    @Mapping(source = "user.name", target = "user.name")
    @Mapping(source = "user.email", target = "user.email")
    TransactionResponse toTransactionResponse(Transaction transaction);

}
