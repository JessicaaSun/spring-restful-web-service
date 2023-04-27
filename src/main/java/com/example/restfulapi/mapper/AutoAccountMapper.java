package com.example.restfulapi.mapper;

import com.example.restfulapi.model.Account;
import com.example.restfulapi.model.response.AccountResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutoAccountMapper {
    // account to account response
    List<AccountResponse> mapToAccountResponse(List<Account> accounts);
    // account response to account
    List<Account> mapToAccount(List<AccountResponse> accountResponses);

}
