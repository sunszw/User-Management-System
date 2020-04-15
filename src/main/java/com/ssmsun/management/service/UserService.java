package com.ssmsun.management.service;


import com.ssmsun.management.entity.User;
import com.ssmsun.management.global.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    void phoneCode(String phone);

    void phoneCode2(Integer userid) throws UserNotFoundException;

    void regUser(User user, String code) throws Exception;

    String login(String username, String password) throws Exception;

    List<User> userInfo(Integer page) throws UserNotFoundException;

    Integer userTotal();

    User person(Integer userid) throws UserNotFoundException;

    void emailCode(Integer userid) throws MessagingException;

    void userConfirm(Integer userid, String code) throws Exception;

    void updatePassword(Integer userid, String password, String newPwd, String code) throws Exception;

    void updateInfo(Integer userid, User user) throws Exception;

    String updateAvatar(Integer userid, MultipartFile file) throws Exception;

    void subUser(Integer userid) throws Exception;

    void downLoadUserData(HttpServletResponse response) throws Exception;


}
