package com.community.config.auth.properties;

import java.util.Date;

public interface JwtProperties {
    /*code:1 정상 응답, -1:실패 응답, 2:로그인 재요청*/
    public static String ACCESS_SECRET = "secretkeyhohoho";
    public static String REFRESH_SECRET = "jwt_refresh_secret";
    public static String SUBJECT = "LEETOKEN";
    public static long ACCESS_EXPIRED_TIME = (10*60*60*10*10)/60L;//1시간
    public static long REFRESH_EXPIRED_TIME = 10*60*60*10*10*24*14L;//2주
    public static final String HEADER = "Authorization";
    public static final String AUTH = "Bearer ";
    public static String ACCESS_NAME = "ACCESS_TOKEN";
    public static String REFRESH_NAME = "REFRESH_TOKEN";
}
