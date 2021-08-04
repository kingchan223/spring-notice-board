package com.community.config.intercepter;

import com.community.config.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInintercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURL = request.getRequestURI();
        log.info("인증체크 인터셉터 실행: {}", requestURL);

        HttpSession session = request.getSession(false);
        if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL=" + requestURL);
            return false;
        }
        return true;
    }
}
