package com.example.restfulapi.repository;

import com.example.restfulapi.model.Account;
import com.example.restfulapi.model.User;
import com.example.restfulapi.model.UserAccount;
import com.example.restfulapi.model.request.UserRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {
    @Result(column = "id", property = "userId")
    @Select("select * from users_tb")
    List<User> allUsers();
    List<User> findUserByName(String username);
    @Select("insert into users_tb (username, gender, address) values (#{user.username}, #{user.gender}, #{user.address}) returning id")
    int createNewUser(@Param("user") UserRequest user);
    @Result(column = "id", property = "userId")
    @Select("select * from users_tb where id = #{id}")
    User findUSerById(int id);

    @Delete("delete from users_tb where id = #{id}")
    int removeUser(int id);

    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "id", property = "accounts", many=@Many(select = "findAccountByUserId"))
    })
    @Select("select * from users_tb")
    List<UserAccount> getAllUserAccount();

    @Results({
            @Result(column = "account_name", property = "accountName"),
            @Result(column = "account_no", property = "accountNumber"),
            @Result(column = "transfer_limit", property = "transferLimit"),
            @Result(column = "account_type", property = "accountType", one = @One(select = "com.example.restfulapi.repository.AccountRepository.getAccountTypeById"))
    })
    @Select("select * from useraccount_tb inner join account_tb a on a.id = useraccount_tb.account_id\n" +
            "where user_id = #{id}")
    List<Account> findAccountByUserId(int id);

    @Update("update users_tb set username=#{user.username}, gender=#{user.gender}, address=#{user.address} where id = #{id} returning #{id}")
    int updateUser(@Param("user") User user, int id);

}
