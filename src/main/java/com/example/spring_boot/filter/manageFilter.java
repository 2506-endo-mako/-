package com.example.spring_boot.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class manageFilter implements Filter {
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
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("無効なアクセスです");
            session.setAttribute("errorMessages", errorMessages);
            //TOP画面にリダイレクト
            httpResponse.sendRedirect("/top");
        }

    }

//    @Override
//    public void init(FilterConfig config) {
//    }

    @Override
    public void destroy() {
    }

}
