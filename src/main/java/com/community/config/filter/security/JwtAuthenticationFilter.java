package com.community.config.filter.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.config.auth.properties.JwtProperties;
import com.community.domain.dto.CMRespDto;
import com.community.domain.dto.member.LoginReqDto;
import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.service.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@AllArgsConstructor
public class JwtAuthenticationFilter implements Filter{


    private MemberService memberService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (!req.getMethod().equals("POST")) {
            resp.setContentType("text/plain; charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.println("잘못된 요청입니다");
            out.flush();
            return;
        }
        // HttpServletRequest
        // POST 요청이 올때만 실행!!
        System.out.println("로그인 인증 필터 JwtAuthenticationFilter 동작 시작");

        // 1. username, password 받아야 함. (req) - json으로 받기 - 버퍼로 읽기
        ObjectMapper om = new ObjectMapper();
//        System.out.println("req.getInputStream()"+req.getInputStream().);
        LoginReqDto loginReqDto = om.readValue(req.getInputStream(), LoginReqDto.class);
        System.out.println("다운 받은 데이터 : " + loginReqDto);
        // 2. memberService로 로그인하기
        Member loginMember = memberService.login(loginReqDto.getLoginId(), loginReqDto.getPassword());

        // 3-1. null이면 로그인 실패.
        if (loginMember == null) {
            System.out.println("로그인실패!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            resp.setContentType("text/plain; charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.println("인증 되지 않았습니다. 다시 인증해주세요");
            out.flush();
            return;
        // 3-2. null아니면 토큰 발급하고 response에 넣어주기
        } else {
            System.out.println("로그인성공!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            String jwtToken = JWT.create()
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRED_TIME))
                    .withClaim("loginId", loginMember.getLoginId())
                    .withClaim("email", loginMember.getEmail())
                    .withClaim("id", loginMember.getId())
                    .withClaim("role", loginMember.getRole())
                    .sign(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET));
            System.out.println("jwtToken = " + jwtToken);
            // 헤더 키값 = RFC문서
            resp.setHeader("Authorization", JwtProperties.AUTH + jwtToken);

            CMRespDto<MemberDto> cmRespDto =
                    new CMRespDto<>(1, "success", MemberDto.createMemberDto(loginMember));

            // 4. response에 Dto객체까지 넣고 돌려주기
            String cmRespDtoJson = om.writeValueAsString(cmRespDto);
            PrintWriter out = resp.getWriter();
            out.print(cmRespDtoJson); // CMRespDto
            out.flush();
        }
    }
}
