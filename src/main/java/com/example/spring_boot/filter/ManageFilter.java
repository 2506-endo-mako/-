package com.example.spring_boot.filter;

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

public class ManageFilter implements Filter {
    @Autowired
    HttpSession session;
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        //型変換
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        session = httpRequest.getSession(false);

        if (session != null && session.getAttribute("userManage") != null){
            chain.doFilter(httpRequest,httpResponse);
        } else {
            session = httpRequest.getSession(true);
            //エラーメッセージをセット
            session.setAttribute("errorMessages", "無効なアクセスです");
            //TOP画面にリダイレクト
            httpResponse.sendRedirect("/top");
        }

    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }

}
