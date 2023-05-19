package com.example.restfulapi.controller;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.request.LoginRequest;
import com.example.restfulapi.model.response.LoginResponse;
import com.example.restfulapi.service.UserService;
import com.example.restfulapi.utils.Response;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationRestController {
    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;
    public AuthenticationRestController(UserService userService, AuthenticationProvider authenticationProvider){
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
    }
    @PostMapping("/register")
    public Response<?> register(){
        return null;
    }

    @GetMapping("/login")
    public Response<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername()
                ,loginRequest.getPassword()
        );
        authentication = authenticationProvider.authenticate(authentication);
        // raise exception when username or password is invalid
        String tokenFormat = authentication.getName() + ":" + authentication.getCredentials();
        String tokenFormatEncoded = Base64.getEncoder().encodeToString(tokenFormat.getBytes());
        String token = "Basic " + tokenFormatEncoded;
        LoginResponse response = new LoginResponse();
        response.setToken(token);

        User user = userService.findUserByName(loginRequest.getUsername()).stream().findFirst().orElse(null);
        response.setUser(user);
        return Response.<LoginResponse>ok().setPayload(response).setMessage("Successfully login, please use your token to authenticate!");
    }

    @PatchMapping("/reset-password")
    public Response<?> resetPassword(){
        return null;
    }

    // verify email
}
