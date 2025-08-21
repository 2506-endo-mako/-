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

        // 型変換
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // セッションが存在しない場合は null を返す
        HttpSession session = httpRequest.getSession(false);

        // 管理者権限を許可するかどうかのフラグ
        boolean isAuthorized = false;

        // セッションが存在するかを確認
        if (session != null) {
            // セッションから departmentId を取得し、nullチェックと型キャストを行う
            Object departmentIdObj = session.getAttribute("departmentId");
            if (departmentIdObj instanceof Integer) {
                Integer departmentId = (Integer) departmentIdObj;
                // departmentIdがnullではなく、かつ1であるかを確認
                if (departmentId != null && departmentId.equals(1)) {
                    isAuthorized = true;
                }
            }
        }

        // 許可された場合は、次のフィルター、またはサーブレットに処理を渡す
        if (isAuthorized) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            // 許可されない場合は、エラーメッセージをセットしてリダイレクト
            // セッションが存在しない場合は新しく作成
            if (session == null) {
                session = httpRequest.getSession(true);
            }
            session.setAttribute("errorMessages", "無効なアクセスです");
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
