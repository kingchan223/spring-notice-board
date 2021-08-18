package com.community.config.auth.properties;

import java.util.Date;

public interface JwtProperties {
    public static String ACCESS_SECRET = "hahahaquququ";
    public static String REFRESH_SECRET = "jwt_refresh_secret";
    public static String SUBJECT = "LEETOKEN";
    public static long ACCESS_EXPIRED_TIME = 10*60*60*10*10L;//1시간
    public static long REFRESH_EXPIRED_TIME = 10*60*60*10*10*24*14L;//2주
    public static final String HEADER = "Authorization";
    public static final String AUTH = "Bearer ";
}
