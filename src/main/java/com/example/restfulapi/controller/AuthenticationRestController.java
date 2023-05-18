package com.example.restfulapi.controller;

import com.example.restfulapi.utils.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationRestController {
    @PostMapping("/register")
    public Response<?> register(){
        return null;
    }

    @GetMapping("/login")
    public Response<?> login(){
        return null;
    }

    @PatchMapping("/reset-password")
    public Response<?> resetPassword(){
        return null;
    }

    // verify email
}
