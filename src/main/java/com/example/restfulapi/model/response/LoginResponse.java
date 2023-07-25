package com.example.restfulapi.model.response;

import com.example.restfulapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String AccessToken;
    private User user;
    private String tokenType;
}
