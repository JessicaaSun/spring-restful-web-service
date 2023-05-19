package com.example.restfulapi.service;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;
import com.example.restfulapi.model.request.UserRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    PageInfo<User> allUsers(int page, int size, String name);

    List<User> findUserByName(String username);
    User findUserByID(int id);
    int createNewUser(UserRequest user);
    int updateUser(User user, int id);
    int removeUser(int id);
    List<UserAccount> getAllUserAccount();

}
