package com.example.restfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private int id;
    private int senderId;
    private int receiverId;
    private float amount;
    private String remark;
    private LocalDate transferAt;
}
