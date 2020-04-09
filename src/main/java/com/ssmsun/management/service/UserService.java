package com.ssmsun.management.service;


import com.ssmsun.management.entity.User;
import com.ssmsun.management.global.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    void phoneCode(String phone);

    void regUser(User user, String code) throws Exception;

    String login(String username, String password) throws Exception;

    List<User> userInfo() throws UserNotFoundException;

    User person(Integer userid) throws UserNotFoundException;

    void emailCode(Integer userid) throws MessagingException;

    void userConfirm(Integer userid,String code) throws Exception;

    String updateAvatar(Integer userid,MultipartFile file) throws Exception;





}