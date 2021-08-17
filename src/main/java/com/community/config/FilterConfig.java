package com.community.config;

import com.community.config.filter.LogFilter;
import com.community.config.filter.security.CorsFilter;
import com.community.config.filter.security.JwtAuthenticationFilter;
import com.community.config.filter.security.JwtAuthorizationFilter;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final MemberService memberService;

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter(){
//        System.out.println("CORS 필터 등록");
//        FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>(new CorsFilter());
//        corsFilter.addUrlPatterns("/**");
//        corsFilter.setOrder(0);
//        return corsFilter;
//    }

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter(){
        FilterRegistrationBean<LogFilter> logFilter = new FilterRegistrationBean<>(new LogFilter());
        logFilter.addUrlPatterns("/*");
        logFilter.setOrder(1);
        return logFilter;
    }


    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(){
        System.out.println("jwtAuthenticationFilter 필터 등록");
        FilterRegistrationBean<JwtAuthenticationFilter> authenticationFilter = new FilterRegistrationBean<>(new JwtAuthenticationFilter(memberService));
        authenticationFilter.addUrlPatterns("/api/login");
        authenticationFilter.setOrder(2);
        return authenticationFilter;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter(){
        System.out.println("jwtAuthorizationFilter 필터 등록");
        FilterRegistrationBean<JwtAuthorizationFilter> authorizationFilter = new FilterRegistrationBean<>(new JwtAuthorizationFilter());
        authorizationFilter.addUrlPatterns("/api/member/*");
        authorizationFilter.addUrlPatterns("/api/board/*");
        authorizationFilter.setOrder(3);
        return authorizationFilter;
    }

//    @Bean
//    public FilterRegistrationBean<JwtAdminAuthorizationFilter> jwtAdminAuthorizationFilter(){
//        System.out.println("jwtAuthorizationFilter 필터 등록");
//        FilterRegistrationBean<JwtAdminAuthorizationFilter> adminAuthorizationFilter = new FilterRegistrationBean<>(new JwtAdminAuthorizationFilter());
//        adminAuthorizationFilter.addUrlPatterns("/api/admin/*");
//        adminAuthorizationFilter.setOrder(2);
//        return adminAuthorizationFilter;
//    }
}
