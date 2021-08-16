package com.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CommunityApplication {

	private static final String PROPERTIES =  "spring.config.location="
			+"classpath:/application.yml"
			+",classpath:/application-oauth.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(CommunityApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}
}
