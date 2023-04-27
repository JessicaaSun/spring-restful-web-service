package com.example.restfulapi.repository;

import com.example.restfulapi.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {
    @Result(column = "id", property = "userId")
    @Select("select * from users_tb")
    List<User> allUsers();
    List<User> findUserByName(String username);
    @Insert("insert into users_tb (username, gender, address) values (#{user.username}, #{user.gender}, #{user.address})")
    int createNewUser(@Param("user") User user);
    int updateUser(User user);
    @Result(column = "id", property = "userId")
    @Select("select * from users_tb where id = #{id}")
    User findUSerById(int id);
    int removeUser(int id);

}
