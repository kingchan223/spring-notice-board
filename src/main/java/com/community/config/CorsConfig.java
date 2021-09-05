package com.community.config;
import com.community.config.auth.properties.JwtProperties;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);//내 서버의 json응답을 자바스크립트가 처리할 수 있게 설정하는 것.
        config.addAllowedOrigin("*");//해당 ip에 응답을 허용
//        config.addAllowedOriginPattern("http://localhost:3000");
        config.addAllowedHeader("*");//모든 header에 응답을 허용
        config.addAllowedMethod("*");//모든 post, get, put, delete, patch 요청을 허용하겠다.
        config.setMaxAge(3600L);

//        ACCESS-ORIGIN-EXPOSED-HEADERS
        config.addExposedHeader(JwtProperties.HEADER);
        config.addExposedHeader(JwtProperties.ACCESS_NAME);
        config.addExposedHeader(JwtProperties.REFRESH_NAME);
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}