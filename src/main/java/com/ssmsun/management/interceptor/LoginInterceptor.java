package com.ssmsun.management.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            token = "";
        }
        Object data = redisTemplate.opsForValue().get(token);
        if (data == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        redisTemplate.expire(token, 10, TimeUnit.MINUTES);
        return true;

    }
}
