package com.community.config.filter.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Slf4j
public class CorsFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("CORS 필터 작동");
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,PUT,DELETE,OPTIONS");
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        // 해당 헤더가 없으면 아래 7가지의 header값만 응답할 수 있다.
        //Cache-Control
        //Content-Language
        //Content-Length
        //Content-Type
        //Expires
        //Last-Modified
        //Pragma
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        chain.doFilter(request, response);
    }
}
