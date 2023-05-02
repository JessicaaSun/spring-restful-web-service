package com.example.restfulapi.repository;

import com.example.restfulapi.model.Account;
import com.example.restfulapi.model.AccountType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountRepository {

    @Results({
            @Result(property = "password",column = "passcode"),
            @Result(property = "accountName",column = "account_name"),
            @Result(property = "accountNumber",column = "account_no"),
            @Result(property = "transferLimit",column = "transfer_limit"),
            @Result(property = "accountType",column = "account_type", one = @One(select = "getAccountTypeById"))
    })
    @Select("select * from account_tb")
    List<Account> getAllAccounts();

    @Result(property = "accountName", column = "name")
    @Select("select * from accounttype_tb where id=#{account_type}")
    AccountType getAccountTypeById(int account_type);
    String getProfile();
    int createAccount(Account account);
    int updateAccount(Account account,int id);
    Account findAccountByID(int id);

}

