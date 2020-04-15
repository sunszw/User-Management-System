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
        paths.add("/user/phone");
        paths.add("/user/reg");
        paths.add("/user/login");

        paths.add("/user/jq/**");
        paths.add("/picture/**");

        paths.add("/web/index.html");
        paths.add("/web/reg.html");
        paths.add("/web/login.html");
        paths.add("/web/info.html");
        paths.add("/web/person.html");
        paths.add("/web/login.html");

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
