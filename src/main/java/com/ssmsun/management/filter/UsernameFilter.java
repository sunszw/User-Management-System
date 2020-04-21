package com.ssmsun.management.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

@WebFilter
public class UsernameFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取前端传递的所有参数名的枚举
        Enumeration pNames = request.getParameterNames();
        String value = "";
        while (pNames.hasMoreElements()) {
            //获取参数名
            String name = (String) pNames.nextElement();
            if ("username".equals(name)) {
                //获取参数值
                value = request.getParameter(name);
            }
        }
        File file = new File("E:\\IdeaProject\\User Management System\\src\\main\\resources\\static\\lexicon\\敏感词库.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String text = "";
        while ((text = bufferedReader.readLine()) != null) {
            if (value.contains(text)) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");

                Map<String,Object> map = new HashMap<>();
                map.put("state",700);
                map.put("message","用户名含有非法词汇，请修改后重新提交!");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(map);

                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.close();
                return;
            }
        }

        filterChain.doFilter(request, response);

    }
}
