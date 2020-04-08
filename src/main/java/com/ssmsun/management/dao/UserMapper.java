package com.ssmsun.management.dao;

import com.ssmsun.management.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {

    Integer addUser(User user);

    User getUser(String username);

    List<User> getAllUser();

    User person(Integer userid);

    Integer ModifUser(User user);

    Integer subUserByid(Integer userid);

    List<User> getPhone();

}
