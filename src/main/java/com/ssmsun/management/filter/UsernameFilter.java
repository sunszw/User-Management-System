package com.ssmsun.management.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter
public class UsernameFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取前端传递的所有参数名的枚举
        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()){
            String name=(String)pNames.nextElement();
            //获取参数值
            String value =request.getParameter(name);
            System.out.println(value);
        }

        filterChain.doFilter(request,response);

    }
}
