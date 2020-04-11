package com.ssmsun.management.controller;


import com.ssmsun.management.entity.User;
import com.ssmsun.management.global.exception.UserNotFoundException;
import com.ssmsun.management.service.impl.UserServiceImpl;
import com.ssmsun.management.util.json.Json;
import com.ssmsun.management.util.verify.token.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ssmsun.management.global.GlobalExceptionHandler.SUCCESS;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JWTUtil jwtUtil;

    @GetMapping(path = "phone")
    @ResponseBody
    public Json<Void> code(User user) {
        userService.phoneCode(user.getPhone());
        return new Json<>(SUCCESS);
    }

    @GetMapping(path = "phone2")
    @ResponseBody
    public Json<Void> code2(HttpServletRequest request) throws UserNotFoundException {
        Integer userid = getUserIdFromToken(request);
        userService.phoneCode2(userid);
        return new Json<>(SUCCESS);
    }

    @GetMapping(path = "email")
    @ResponseBody
    public Json<Void> emailCode(HttpServletRequest request) throws Exception {
        Integer userid = getUserIdFromToken(request);
        userService.emailCode(userid);
        return new Json<>(SUCCESS);
    }

    @PostMapping(path = "reg")
    @ResponseBody
    public Json<String> reg(User user, String code) throws Exception {
        userService.regUser(user, code);
        return new Json<>(SUCCESS);
    }

    @PostMapping(path = "login")
    @ResponseBody
    public Json<String> login(User user) throws Exception {
        String data = userService.login(user.getUsername(), user.getPassword());
        return new Json<>(SUCCESS, data);
    }


    @GetMapping(path = "info")
    @ResponseBody
    public Json<List<User>> info() throws UserNotFoundException {
        List<User> data = userService.userInfo();
        return new Json<>(SUCCESS, data);
    }

    @GetMapping(path = "person")
    @ResponseBody
    public Json<User> person(HttpServletRequest request) throws UserNotFoundException {
        Integer userid = getUserIdFromToken(request);
        User data = userService.person(userid);
        return new Json<>(SUCCESS, data);
    }

    @PostMapping(path = "confirm")
    @ResponseBody
    public Json<Void> confirm(HttpServletRequest request, String code) throws Exception {
        Integer userid = getUserIdFromToken(request);
        userService.userConfirm(userid, code);
        return new Json<>(SUCCESS);
    }

    @PostMapping(path = "password")
    @ResponseBody
    public Json<String> password(HttpServletRequest request, @RequestParam("origPwd") String password,@RequestParam("newPwd") String newPwd, String code) throws Exception {
        Integer userid = getUserIdFromToken(request);
        userService.updatePassword(userid, password, newPwd, code);
        return new Json<>(SUCCESS);
    }

    @PostMapping(path = "avatar")
    @ResponseBody
    public Json<String> avatar(HttpServletRequest request, MultipartFile file) throws Exception {
        Integer userid = getUserIdFromToken(request);
        String name = userService.updateAvatar(userid, file);
        return new Json<>(SUCCESS, name);
    }

    @PostMapping(path = "change")
    @ResponseBody
    public Json<String> changeInfo(HttpServletRequest request, User user) throws Exception {
        Integer userid = getUserIdFromToken(request);
        userService.updateInfo(userid,user);
        return new Json<>(SUCCESS);
    }

    @PostMapping(path = "{delid}/delete")
    @ResponseBody
    public Json<String> delete(@PathVariable("delid") Integer userid) throws Exception {
        userService.subUser(userid);
        return new Json<>(SUCCESS);
    }

    private Integer getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String value = redisTemplate.opsForValue().get(token).toString();
        Integer userid = Integer.valueOf(jwtUtil.parseToken(value));
        return userid;
    }

}
