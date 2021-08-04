package com.community.config.filter;

import com.community.config.SessionConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Log Filter - doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        try{
            log.info("REQUEST : [{}][{}]", uuid, requestURI);
            log.info("CLIENT SESSIOM = {}", httpRequest.getSession().getAttribute(SessionConst.LOGIN_MEMBER));
            log.info("REQUEST URL = {}", requestURI);
            chain.doFilter(request, response);
        }catch(Exception e){
            throw e;
        }finally{
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
