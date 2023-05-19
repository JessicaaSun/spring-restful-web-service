package com.example.restfulapi.service.serviceImpl;

import com.example.restfulapi.model.User;
import com.example.restfulapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    //load user form the database
    // inject repository
    UserRepository userRepository;
    public UserDetailServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Optional<Object> -> prevent null pointer exception
        User authenticatedUser = userRepository.findUserByName(username).stream().findFirst().orElse(null);
        System.out.println("Here is the authenticatedUser : "+authenticatedUser);
        if(authenticatedUser==null){
            throw new UsernameNotFoundException("AuthenticatedUser doesn't exist");
        }
        // create an object of user detail type
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) org.springframework.security.core.userdetails.User.builder()
                .username(authenticatedUser.getUsername())
                .password(authenticatedUser.getPassword())
                .roles("USER").build();
        return user;
    }
}
