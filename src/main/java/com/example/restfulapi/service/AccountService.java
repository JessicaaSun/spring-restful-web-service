package com.example.restfulapi.service;

import com.example.restfulapi.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    int createAccount(Account account);
    int updateAccount(Account account,int id);
    Account findAccountById(int id);
}
