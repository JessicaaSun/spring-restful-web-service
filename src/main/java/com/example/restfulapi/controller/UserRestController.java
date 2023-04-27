package com.example.restfulapi.controller;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;
import com.example.restfulapi.service.UserService;
import com.example.restfulapi.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
    UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all-users")
    List<User> getAllUser(){
        return userService.allUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id){
        return userService.findUserByID(id);
    }

    @PostMapping("/new-user")
    public String createUser(@RequestBody User user){
        try{
            int affectedRow = userService.createNewUser(user);
            if(affectedRow>0)
                return "Create user successfully!";
            else return "Cannot create new user!";
        }catch (Exception exception){
            return exception.getMessage();
        }
    }

    @GetMapping("/all-user-accounts")
    public Response<List<UserAccount>> getAllUserAccounts(){
        try {
            List<UserAccount> userAccounts = userService.getAllUserAccount();
            return Response.<List<UserAccount>>ok().setPayload(userAccounts).setMessage("Successfully retrieve all user accounts");
        } catch (Exception exception){
            return Response.<List<UserAccount>>ok().setMessage("Failed to retrieve all user accounts!").setSuccess(false);
        }
    }

}
