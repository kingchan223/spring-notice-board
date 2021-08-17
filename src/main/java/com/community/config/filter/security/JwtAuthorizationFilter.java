package com.community.config.filter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.community.config.auth.properties.JwtProperties;
import com.community.domain.entity.Member;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("사용자 인가 필터 JwtAuthorizationFilter 동작 시작");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String jwtToken = req.getHeader(JwtProperties.HEADER);

        System.out.println("토큰 : " + jwtToken);

        if (jwtToken == null) {
            PrintWriter out = resp.getWriter();
            resp.setStatus(403);
            out.println("jwtToken not found");
            out.flush();
        }
        else{
            jwtToken = jwtToken.replace(JwtProperties.AUTH, "");
            // System.out.println(jwtToken);

            try{
                DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET)).build().verify(jwtToken);

                Integer loginId = decodeJwt.getClaim("loginId").asInt();
                Long id = decodeJwt.getClaim("id").asLong();
                String role = decodeJwt.getClaim("role").asString();
                Member loginUser = Member.createMember(Long.valueOf(loginId), null, null, null, role);

                HttpSession session = req.getSession();
                session.setAttribute("principal", loginUser);
                req.setAttribute("memberrId", id);
                chain.doFilter(req, resp);
            }catch (Exception e){
                PrintWriter out = resp.getWriter();
                out.println("verify fail");
                out.flush();
            }
        }
    }
}
