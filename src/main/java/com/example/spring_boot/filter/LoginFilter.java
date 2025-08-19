package com.example.spring_boot.filter;

import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {
    @Autowired
    HttpSession session;
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        //型変換
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        if (session != null && session.getAttribute("loginUser") != null){
            chain.doFilter(request, response);
        } else {
            session = httpRequest.getSession(true);
            session.setAttribute("errorMessages", "ログインしてください");
            //ログイン画面にリダイレクト
            httpResponse.sendRedirect("/");
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }

}
