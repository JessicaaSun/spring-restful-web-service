package com.example.restfulapi.service.serviceImpl;

import com.example.restfulapi.model.Transaction;
import com.example.restfulapi.model.TransactionV2;
import com.example.restfulapi.repository.TransactionRepository;
import com.example.restfulapi.service.TransactionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionV2> getAllTransactionsV2() {
        return transactionRepository.getAllTransactionsV2();
    }

    @Override
    public PageInfo<Transaction> getAllTransaction(int page, int size, int filterId) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(transactionRepository.allTransactions(filterId));
//        return new PageInfo<>(transactionRepository.allTransactions());
    }

    @Override
    public int createNewTransaction(Transaction transaction) {
        return transactionRepository.createNewTransaction(transaction);
    }

    @Override
    public int updateTransaction(Transaction transaction, int transactionId) {
        return transactionRepository.updateTransaction(transaction, transactionId);
    }

    @Override
    public int deleteTransaction(int transactionId) {
        return transactionRepository.deleteTransaction(transactionId);
    }

}
