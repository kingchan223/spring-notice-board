package com.community.config.filter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.community.config.auth.properties.JwtProperties;
import com.community.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JwtAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("사용자 인가 필터 JwtAuthorizationFilter 동작 시작");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String jwtToken = req.getHeader(JwtProperties.HEADER);

        log.info("토큰 :{} ", jwtToken);

        if (jwtToken == null) {
            PrintWriter out = resp.getWriter();
            resp.setStatus(403);
            out.println("jwtToken not found");
            out.flush();
        }
        else{
            jwtToken = jwtToken.replace(JwtProperties.AUTH, "");
             System.out.println(jwtToken);

            try{
//                DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET)).build().verify(jwtToken);

                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JwtProperties.ACCESS_SECRET))
                        .acceptExpiresAt(60*10*10) // 만료일 -4일
                        .build();

                DecodedJWT jwt = verifier.verify(jwtToken);
                Integer loginId = jwt.getClaim("loginId").asInt();
                Long id = jwt.getClaim("id").asLong();
                String role = jwt.getClaim("role").asString();
                Member loginUser = Member.createMember(Long.valueOf(loginId), null, null, null, role);

                HttpSession session = req.getSession();
                session.setAttribute("principal", loginUser);
                System.out.println("id = " + id);
                req.setAttribute("memberId", id);
                chain.doFilter(req, resp);
            }catch (Exception e){
                log.error("토큰 검증실패");
                PrintWriter out = resp.getWriter();
                out.println("verify fail");
                out.flush();
            }
        }
    }
}
