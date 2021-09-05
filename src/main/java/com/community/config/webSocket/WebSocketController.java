package com.community.config.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@CrossOrigin
@Controller
public class WebSocketController {

    // 특정 사용자가 "/hello"로 메시지를 보내면  "/topic/greetings"의 토픽을 구독하고 있던 구독자들에게 메시지가 전송된다.
    // 보통 @SendTo의 경우에는 /topic으로 시작하고, @SenToUser의 경우에는 /queue로 시작한다.
    @MessageMapping("/hello")//The @MessageMapping annotation ensures that, if a message is sent to the /hello destination, the greeting() method is called.
    @SendTo("/topic/greetings")//1:n 구조일 때. 특정 사용자가 "/hello"로 메시지를 전송하면 "/topic/greetings"을 구독하고 있는 사람들에게 해당 메시지가 수신된다.
    @CrossOrigin
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(HtmlUtils.htmlEscape(message.getUsername()+":"+message.getContent()));//The return value is broadcast to all subscribers of /topic/greetings
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("chatMessage = " + chatMessage.getContent());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("chatMessage = " + chatMessage.getContent());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}