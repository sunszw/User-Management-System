package com.ssmsun.management.controller;


import com.ssmsun.management.entity.User;
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
    public Json<List<User>> info() {
        List<User> data = userService.userInfo();
        return new Json<>(SUCCESS, data);
    }

    @GetMapping(path = "person")
    @ResponseBody
    public Json<User> person(HttpServletRequest request) {
        String token = request.getHeader("token");
        String value = redisTemplate.opsForValue().get(token).toString();
        Integer userid = Integer.valueOf(jwtUtil.parseToken(value));
        User data = userService.person(userid);
        return new Json<>(SUCCESS, data);
    }

    @GetMapping(path = "code")
    @ResponseBody
    public Json<Void> code(User user) {
        userService.Code(user.getPhone());
        return new Json<>(SUCCESS);
    }


}
