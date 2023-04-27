package com.example.restfulapi.controller;

import com.example.restfulapi.model.User;
import com.example.restfulapi.service.UserService;
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

}
