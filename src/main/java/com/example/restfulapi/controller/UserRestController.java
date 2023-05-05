package com.example.restfulapi.controller;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;
import com.example.restfulapi.model.request.UserRequest;
import com.example.restfulapi.service.UserService;
import com.example.restfulapi.utils.Response;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
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
    public Response<PageInfo<User>> getAllUser(@RequestParam (defaultValue = "1") int page, @RequestParam (defaultValue = "5") int size, @RequestParam(defaultValue = "", required = false) String username){
        try{
            PageInfo<User> users = userService.allUsers(page, size, username);
            return Response.<PageInfo<User>>ok().setMessage("Successfully retrieved all users!").setPayload(users);
        } catch (Exception exception) {
            return Response.<PageInfo<User>>exception().setMessage("Fail to retrieve all users!").setSuccess(false);
        }
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
    public Response<User> createUser(@Valid @RequestBody UserRequest user){
        try{
            // u can use mapstruct or modelmapper if data is too many
            int userId = userService.createNewUser(user);
            System.out.println("userId " + userId);
            if(userId >0){
                User response = new User().setUsername(user.getUsername()).setAddress(user.getAddress()).setUserId(userId).setGender(user.getGender());
                return  Response.<User>createSuccess().setPayload(response).setMessage("Successfully created a new user!");
            } else {
                return  Response.<User>badRequest().setMessage("Fail to create a new user").setSuccess(false);
            }
        }catch (Exception exception){
            return Response.<User>badRequest().setSuccess(false).setMessage("Failed to create new user!");
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
//             Shorter way to do this
            int affectedRow = userService.removeUser(id);
            if(affectedRow >0){
                return Response.<User>deleteSuccess().setMessage("Successfully deleted a user with id "+id);
            } else {
                return Response.<User>notFound().setSuccess(false).setMessage("User with id = "+id+" does not exist in our system!");
            }
//            if(isUserExists(id)){
//                userService.removeUser(id);
//                return Response.<User>deleteSuccess().setMessage("Successfully deleted a user with id "+id);
//            }
//            else {
//                return userNotFound(id);
//            }
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
