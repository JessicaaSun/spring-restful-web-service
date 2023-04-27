package com.example.restfulapi.service.serviceImpl;

import com.example.restfulapi.model.Account;
import com.example.restfulapi.repository.AccountRepository;
import com.example.restfulapi.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    final private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public int createAccount(Account account) {
        return 0;
    }

    @Override
    public int updateAccount(Account account, int id) {
        return 0;
    }

    @Override
    public Account findAccountById(int id) {
        return accountRepository.findAccountByID(id);
    }
}
