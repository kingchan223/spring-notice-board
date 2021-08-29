package com.community.config.filter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.community.config.auth.properties.JwtProperties;
import com.community.domain.dto.CMRespDto;
import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.service.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {

    private final MemberService memberService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        log.info("사용자 인가 필터 JwtAuthorizationFilter 동작 시작");
        ObjectMapper om = new ObjectMapper();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String accessToken = req.getHeader(JwtProperties.ACCESS_NAME);
        String refreshToken = req.getHeader(JwtProperties.REFRESH_NAME);

        log.info("ACCESS 토큰 :{} ", accessToken);
        log.info("REFRESH 토큰 :{} ", refreshToken);

        if (accessToken == null && refreshToken == null) {/*토큰이 아예 없음*/
            log.info("토큰 없음");
            // 토큰이 아예 없으므로, 로그인을 요청하는 코드 -1을 보낸다.
            CMRespDto<MemberDto> cmRespDto = new CMRespDto<>(-1, "no token", null);
            String cmRespDtoJson = om.writeValueAsString(cmRespDto);
            PrintWriter out = resp.getWriter();
            out.print(cmRespDtoJson); // CMRespDto
            out.flush();
        }
        else{
            Long id = 100L;
            log.info("토큰 검증 시작");
            assert accessToken != null;
            accessToken = accessToken.replace(JwtProperties.AUTH, "");

            /*access 토큰 검증 시작*/
            try {
                log.info("access토큰 검사 시작");
                DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET)).withIssuer("LEEE").build().verify(accessToken);
                id = decodeJwt.getClaim("id").asLong();
                req.setAttribute("memberId", id);
                log.info("access토큰 검증 성공");
                chain.doFilter(req, resp);

            /* access토큰의 기간이 만료되었다면 refresh토큰을 검사하고 access를 재발급해준다. */
            }catch(TokenExpiredException e){
                log.info("access 토큰의 기간이 만료됨");
                try{/*refresh토큰 검사*/
                    log.info("refresh토큰 검사 AT Authorization");
                    System.out.println("refreshToken = " + refreshToken);
                    refreshToken = refreshToken.replace(JwtProperties.AUTH, "");
                    //가져온 refresh토큰에서 회원 정보를 추출하고,
                    DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC512(JwtProperties.REFRESH_SECRET)).withIssuer("LEEE").build().verify(refreshToken);
                    id = decodeJwt.getClaim("id").asLong();

                    // 회원 정보를 사용하여 access토큰을 만들어 준다.
                    Member member = memberService.findUser(id);
                    String accessTokenNEW = TokenManager.createAccessToken(member);

                    resp.setHeader("ACCESS_TOKEN", JwtProperties.AUTH + accessTokenNEW);
                    log.info("refresh토큰 검사 후 access토큰 재발급 성공");
                    req.setAttribute("memberId", id);
                    chain.doFilter(req, resp);

                /*refresh토큰의 기간도 지났다면 재발급하자*/
                }catch(TokenExpiredException | ServletException e2){
                    log.info("access, refresh 토큰의 기간이 만료됨");
                    log.error("로그인 재요청");

                    CMRespDto<?> cmRespDto = new CMRespDto<>(-1, "login plz~", null);
                    String cmRespDtoJson = om.writeValueAsString(cmRespDto);
                    PrintWriter out = resp.getWriter();
                    out.print(cmRespDtoJson);
                    out.flush();
                }

            }catch (Exception e){
                log.error("그냥 죄다 실패");
                CMRespDto<MemberDto> cmRespDto = new CMRespDto<>(-1, "fail", null);
                String cmRespDtoJson = om.writeValueAsString(cmRespDto);
                PrintWriter out = resp.getWriter();
                out.println(cmRespDtoJson);
                out.flush();
            }
        }
    }
}
