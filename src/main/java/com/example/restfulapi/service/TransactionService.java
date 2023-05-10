package com.example.restfulapi.service;

import com.example.restfulapi.model.Transaction;
import com.example.restfulapi.model.TransactionV2;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    List<TransactionV2> getAllTransactionsV2();
    PageInfo<Transaction> getAllTransaction(int page, int size, int filterId);
    int createNewTransaction(Transaction transaction);
    int updateTransaction(Transaction transaction, int transactionId);
    int deleteTransaction(int transactionId);
}
