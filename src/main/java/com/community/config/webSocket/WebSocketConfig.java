package com.community.config.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin
//@EnableWebSocketMessageBroker enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    @CrossOrigin
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/app");
        config.setApplicationDestinationPrefixes("/topic");//이게 왜 또 붙어서 클라이언트가 "/app/hello"로 메시지를 보내는건지 모르겠음
    }

    @Override
    @CrossOrigin
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();//클라이언트에서 여기로 커넥션을 요청한다(?)
    }


}