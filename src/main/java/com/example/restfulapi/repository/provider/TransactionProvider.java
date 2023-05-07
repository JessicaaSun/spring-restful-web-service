package com.example.restfulapi.repository.provider;

import com.example.restfulapi.model.Transaction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class TransactionProvider {
    public static String getAllTransactions(int filterId){
        return new SQL(){{
            SELECT("*");
            FROM("transactions_tb");
            if(filterId != 0){
                WHERE("id = #{filterId}");
            }
        }}.toString();
    }

    public static String insertTransaction(Transaction transaction) {
        return new SQL() {{
            INSERT_INTO("transactions_tb");
            VALUES("sender_account_id", "#{senderId}");
            VALUES("receiver_account_id", "#{receiverId}");
            VALUES("amount", "#{amount}");
            VALUES("remark", "#{remark}");
            VALUES("transfer_at", "#{transferAt}");
        }}.toString();
    }

    public static String updateTransaction(Transaction transaction, int transactionId){
        return new SQL(){{
            UPDATE("transactions_tb");
                SET("receiver_account_id = #{transaction.receiverId}");
                SET("amount = #{transaction.amount}");
                SET("transfer_at = #{transaction.transferAt}");
            SET("remark = #{transaction.remark}");
            WHERE("id = #{transactionId}");
        }}.toString();
    }

    public static String deleteTransaction(int transactionId){
        return new SQL(){{
            DELETE_FROM("transactions_tb");
            WHERE("id = #{transactionId}");
        }}.toString();
    }
}
