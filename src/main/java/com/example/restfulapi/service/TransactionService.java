package com.example.restfulapi.service;

import com.example.restfulapi.model.Transaction;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    PageInfo<Transaction> getAllTransaction(int page, int size, int filterId);
    int createNewTransaction(Transaction transaction);
    int updateTransaction(Transaction transaction, int transactionId);
    int deleteTransaction(int transactionId);
}
