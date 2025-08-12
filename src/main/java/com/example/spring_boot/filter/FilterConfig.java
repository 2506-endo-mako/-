package com.example.spring_boot.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

//        @Bean
//        public FilterRegistrationBean<loginFilter> loginFilter() {
//            FilterRegistrationBean<loginFilter> bean = new FilterRegistrationBean<>();
//
//            bean.setFilter(new loginFilter());
//            //ログイン情報が必要なURL
//            bean.addUrlPatterns("/new");
//            bean.addUrlPatterns("/UserEdit");
//            bean.addUrlPatterns("/userManage");
//            bean.addUrlPatterns("/signUp");
//            bean.addUrlPatterns("/userEdit/*");
//            bean.setOrder(1);
//            return bean;
//        }

        @Bean
        public FilterRegistrationBean<manageFilter> manageFilter() {
            FilterRegistrationBean<manageFilter> bean = new FilterRegistrationBean<>();

            bean.setFilter(new manageFilter());
            //ログイン情報が必要なURL
            bean.addUrlPatterns("/userManage");
            bean.addUrlPatterns("/signUp");
            bean.addUrlPatterns("/userEdit/*");
            bean.setOrder(2);
            return bean;
        }

}
