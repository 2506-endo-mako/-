package com.example.spring_boot.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

        @Bean
        public FilterRegistrationBean<LoginFilter> loginFilter() {
            FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

            bean.setFilter(new LoginFilter());
            //ログイン情報が必要なURL
            bean.addUrlPatterns("/top");
            bean.addUrlPatterns("/new");
            bean.addUrlPatterns("/UserEdit");
            bean.addUrlPatterns("/userManage");
            bean.addUrlPatterns("/signUp");
            bean.addUrlPatterns("/userEdit/*");
            bean.setOrder(1);
            return bean;
        }

        @Bean
        public FilterRegistrationBean<ManageFilter> ManageFilter() {
            FilterRegistrationBean<ManageFilter> bean = new FilterRegistrationBean<>();

            bean.setFilter(new ManageFilter());
            //ログイン情報が必要なURL
            bean.addUrlPatterns("/userManage");
            bean.addUrlPatterns("/signUp");
            bean.addUrlPatterns("/userEdit/*");
            bean.setOrder(2);
            return bean;
        }

}
