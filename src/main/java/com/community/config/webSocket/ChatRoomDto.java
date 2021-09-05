package com.community.config.webSocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessoions = new HashSet<>();
    //WebSocketSession은 spring에서 websocket connection이 맺어진 세션

    public static ChatRoomDto create(String name){
        ChatRoomDto room = new ChatRoomDto();
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }
}
