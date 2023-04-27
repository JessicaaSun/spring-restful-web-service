package com.example.restfulapi.service;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    List<User> findUserByName();
    User findUserByID(int id);
    int createNewUser(User user);
    int updateUser(User user);
    int removeUser(int id);
    List<UserAccount> getAllUserAccount();

}
