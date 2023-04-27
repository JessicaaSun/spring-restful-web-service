package com.example.restfulapi.model.response;

import com.example.restfulapi.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private int id;
    private String accountName;
    private String accountNumber;
    private String profile;
    private String phone;
    private float transferLimit;
    private AccountType accountType;
}
