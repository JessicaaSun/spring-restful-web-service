package com.example.restfulapi.service;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;
import com.example.restfulapi.model.request.UserRequest;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    List<User> findUserByName();
    User findUserByID(int id);
    int createNewUser(UserRequest user);
    int updateUser(User user, int id);
    int removeUser(int id);
    List<UserAccount> getAllUserAccount();

}
