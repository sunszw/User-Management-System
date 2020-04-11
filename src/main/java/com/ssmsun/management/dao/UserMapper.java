package com.ssmsun.management.dao;

import com.ssmsun.management.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {

    Integer addUser(User user);

    User getUserByUsername(String username);

    List<User> getAllUser();

    User person(Integer userid);

    Integer changeStatus(Integer userid);

    Integer changePwd(Integer userid, String password);

    Integer changeInfoByUserid(Integer userid, String email, String phone, Integer gender);

    Integer changeAvatar(Integer userid, String avatar);

    Integer delUserByid(Integer userid);


}
