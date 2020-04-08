package com.ssmsun.management.service;


import com.ssmsun.management.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void regUser(User user, String code) throws Exception;

    String login(String username, String password) throws Exception;

    List<User> userInfo();

    User person(Integer userid);

    User changeAvatar(MultipartFile file) throws Exception;



    void Code(String phone);

}
