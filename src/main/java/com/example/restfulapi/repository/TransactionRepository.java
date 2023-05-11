package com.example.restfulapi.repository;

import com.example.restfulapi.model.Transaction;
import com.example.restfulapi.model.TransactionV2;
import com.example.restfulapi.model.UserTransaction;
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

    @SelectProvider(type = TransactionProvider.class, method = "getTransactionsV2")
    @Results({
            @Result(column = "sender_account_id", property ="sender",one = @One(select = "getUserTransactionByAccountID")),
            @Result(column = "receiver_account_id", property ="receiver" , one = @One(select="getUserTransactionByAccountID")),
            @Result(column = "transfer_at", property ="transferAt" )
    })
    List<TransactionV2> getAllTransactionsV2();


    @Select("select account_no, account_id, ut.* from useraccount_tb\n" +
            "           inner join users_tb ut on ut.id = useraccount_tb.user_id\n" +
            "            inner join account_tb a on a.id = useraccount_tb.account_id\n" +
            "           where account_id = #{id}")
    @Results(value = {
            @Result(property ="accountId", column ="account_id"),
            @Result(property = "accountNumber",column = "account_no"),
            @Result(property = "user.userId", column = "id"),
            @Result(property = "user.username",column = "username"),
            @Result(property = "user.gender", column = "gender"),
            @Result(property = "user.address", column = "address")
    })
    UserTransaction getUserTransactionByAccountID(int id);

    @InsertProvider(type = TransactionProvider.class, method = "insertTransaction")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createNewTransaction(Transaction transaction);

    @UpdateProvider(type = TransactionProvider.class, method = "updateTransaction")
    int updateTransaction(Transaction transaction, int transactionId);

    @DeleteProvider(type = TransactionProvider.class, method = "deleteTransaction")
    int deleteTransaction(int transactionId);
}
