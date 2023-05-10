package com.example.restfulapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionV2 {
    private int id;
//   private int senderAccountId;

    private UserTransaction sender ;
    //    private int receiverAccountId;
    private UserTransaction receiver;
    private float amount;
    private String remark;
    private Date transferAt;
}
