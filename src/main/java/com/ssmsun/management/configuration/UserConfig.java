package com.ssmsun.management.configuration;



import com.ssmsun.management.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();//注意：需要在初始化时就注入拦截器，否则redisTemplate会为null
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> paths=new ArrayList<>();
        paths.add("/html/index.html");
        paths.add("/html/reg.html");
        paths.add("/html/login.html");
        paths.add("/html/info.html");
        paths.add("/html/person.html");
        paths.add("/html/password.html");
        paths.add("/html/update.html");

        paths.add("/style/**");
        paths.add("/picture/**");

        paths.add("/user/phone");
        paths.add("/user/reg");
        paths.add("/user/login");
        paths.add("/user/download");



        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**").excludePathPatterns(paths);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //处理跨域
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }



}
