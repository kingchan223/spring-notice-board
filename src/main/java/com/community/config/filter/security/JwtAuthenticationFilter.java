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
            System.out.println("로그인실패");
            resp.setContentType("application/json; charset=utf-8");

            CMRespDto<MemberDto> cmRespDto =
                    new CMRespDto<>(-1, "fail", null);
            String cmRespDtoJson = om.writeValueAsString(cmRespDto);
            PrintWriter out = resp.getWriter();
            out.println(cmRespDtoJson);
            out.flush();
        // 3-2. null아니면 토큰 발급하고 response에 넣어주기
        } else {
            System.out.println("로그인성공");

            String accessToken = TokenManager.createAccessToken(loginMember);
            String refreshToken = TokenManager.createRefreshToken(loginMember);

            System.out.println("jwtAccessToken = " + accessToken);
            System.out.println("jwtAccessToken = " + refreshToken);
            // 헤더 키값 = RFC문서
            resp.setHeader("Authorization", JwtProperties.AUTH + accessToken);
            resp.setContentType("application/json; charset=utf-8");

            //헤더에 access, refresh토큰 넣어주기
            resp.setHeader("ACCESS_TOKEN", JwtProperties.AUTH + accessToken);
            resp.setHeader("REFRESH_TOKEN", JwtProperties.AUTH + refreshToken);

            //refresh토큰 DB에 저장
            memberService.addRefreshToken(loginMember.getId(), refreshToken);
            System.out.println("==========================");
            System.out.println("loginMember = " + loginMember.getLoginId());
            System.out.println("loginMember = " + loginMember.getId());
            System.out.println("loginMember = " + loginMember.getName());
            System.out.println("loginMember = " + loginMember.getRole());
            System.out.println("loginMember = " + loginMember.getEmail());
            System.out.println("==========================");
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
