package com.community.config.filter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.config.auth.properties.JwtProperties;
import com.community.domain.entity.Member;

import java.util.Date;

public class TokenManager {


    public static String createAccessToken(Member loginMember){
        String accessToken = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRED_TIME))
                .withIssuer("LEEE")
                .withClaim("loginId", loginMember.getLoginId())
                .withClaim("email", loginMember.getEmail())
                .withClaim("id", loginMember.getId())
                .withClaim("role", loginMember.getRole())
                .sign(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET));
        return accessToken;
    }

    public static String createRefreshToken(Member loginMember){
        String refreshToken = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRED_TIME))
                .withIssuer("LEEE")
                .withClaim("loginId", loginMember.getLoginId())
                .withClaim("email", loginMember.getEmail())
                .withClaim("id", loginMember.getId())
                .withClaim("role", loginMember.getRole())
                .sign(Algorithm.HMAC512(JwtProperties.REFRESH_SECRET));
        return refreshToken;
    }


}
