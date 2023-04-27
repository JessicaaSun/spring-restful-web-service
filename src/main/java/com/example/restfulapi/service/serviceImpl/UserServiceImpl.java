package com.example.restfulapi.service.serviceImpl;

import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;
import com.example.restfulapi.repository.UserRepository;
import com.example.restfulapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.allUsers();
    }

    @Override
    public List<User> findUserByName() {
        return null;
    }

    @Override
    public User findUserByID(int id) {
        return userRepository.findUSerById(id);
    }

    @Override
    public int createNewUser(User user) {
        return userRepository.createNewUser(user);
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int removeUser(int id) {
        return 0;
    }

    @Override
    public List<UserAccount> getAllUserAccount() {
        return userRepository.getAllUserAccount();
    }
}
