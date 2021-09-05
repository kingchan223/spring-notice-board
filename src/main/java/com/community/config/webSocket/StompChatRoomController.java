package com.community.config.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class StompChatRoomController {

    private final SimpMessagingTemplate template;//특정 Borker로 메시지 전달

    @MessageMapping(value="/chat/enter")
    public void enter(ChatMessageDto message){
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/"+message.getRoomId(), message);
    }

    @MessageMapping(value="/chat/message")
    public void message(ChatMessageDto message){
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

    }



}
