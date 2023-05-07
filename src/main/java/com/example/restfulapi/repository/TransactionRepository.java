package com.example.restfulapi.repository;

import com.example.restfulapi.model.Transaction;
import com.example.restfulapi.repository.provider.TransactionProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;
import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface TransactionRepository {

    @Results({
            @Result(property = "senderId", column = "sender_account_id"),
            @Result(property = "receiverId", column = "receiver_account_id"),
            @Result(property = "transferAt", column = "transfer_at")
    })
    @SelectProvider(type = TransactionProvider.class, method = "getAllTransactions")
    List<Transaction> allTransactions(int filterId);

    @InsertProvider(type = TransactionProvider.class, method = "insertTransaction")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createNewTransaction(Transaction transaction);

    @UpdateProvider(type = TransactionProvider.class, method = "updateTransaction")
    int updateTransaction(Transaction transaction, int transactionId);

    @DeleteProvider(type = TransactionProvider.class, method = "deleteTransaction")
    int deleteTransaction(int transactionId);
}
