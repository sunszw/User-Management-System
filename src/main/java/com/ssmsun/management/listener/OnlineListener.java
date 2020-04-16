package com.ssmsun.management.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.Set;


@WebListener
public class OnlineListener implements ServletRequestListener {

    @Autowired
    StringRedisTemplate redisTemplate;

    private static Integer count;

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        Set<String> keys = redisTemplate.keys("*");
        count = keys.size();
        sre.getServletContext().setAttribute("count", count);
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        Set<String> keys = redisTemplate.keys("*");
        count = keys.size();
        sre.getServletContext().setAttribute("count", count);
    }

    public static Integer getCount(){
        return  count;
    }

}
