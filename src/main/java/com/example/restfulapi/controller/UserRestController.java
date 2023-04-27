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

    private boolean isUserExists(int id) {
        User user = userService.findUserByID(id);
        return user != null;
    }
    private Response<User> userNotFound(int id){
        return Response.<User>notFound().setMessage("Cannot find user with id "+id).setSuccess(false).setStatus(Response.Status.NOT_FOUND);
    }

    @GetMapping("/all-users")
    List<User> getAllUser(){
        return userService.allUsers();
    }

    @GetMapping("/{id}")
    public Response<User> getUserById(@PathVariable int id){
        try {
            if(isUserExists((id))){
                return Response.<User>ok().setPayload(userService.findUserByID(id)).setMessage("Successfully retrieved a user with id "+id);
            } else {
                return userNotFound(id);
            }
        } catch (Exception exception) {
            return Response.<User>exception().setSuccess(false).setMessage("Failed to retrieve a user with id "+id);
        }
    }

    @PostMapping("/new-user")
    public Response<User> createUser(@RequestBody User user){
        try{
            userService.createNewUser(user);
            return  Response.<User>createSuccess().setPayload(user).setMessage("Successfully created a new user!");
        }catch (Exception exception){
            return Response.<User>exception().setSuccess(false).setMessage("Failed to create new user!");
        }
    }

    @PutMapping("/{id}")
    public Response<User> updateUser(@PathVariable int id, @RequestBody User user){
        try{
            if(isUserExists(id)){
                user.setUserId(id);
                userService.updateUser(user, id);
                return Response.<User>updateSuccess().setPayload(user).setMessage("Successfully updated a user with id "+id);
            } else {
                return userNotFound(id);
            }
        } catch (Exception exception){
            return Response.<User>exception().setSuccess(false).setMessage("Fail to update a user with id "+id);
        }

    }

    @DeleteMapping("/{id}")
    public Response<User> deleteUser(@PathVariable int id){
        try {
            if(isUserExists(id)){
                userService.removeUser(id);
                return Response.<User>deleteSuccess().setMessage("Successfully deleted a user with id "+id);
            }
            else {
                return userNotFound(id);
            }
        } catch (Exception exception){
            return Response.<User>exception().setMessage("Fail to delete a user with id "+ id).setSuccess(false);
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
