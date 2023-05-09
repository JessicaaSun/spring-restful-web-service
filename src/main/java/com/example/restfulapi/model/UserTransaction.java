package com.example.restfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTransaction {
    private int accountId;
    private String accountNumber;
    private User user;
}
