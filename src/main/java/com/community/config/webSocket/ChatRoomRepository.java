package com.community.config.webSocket;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomDto> chatRoomDtoMap;

    @PostConstruct
    private void init(){
        chatRoomDtoMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRooms(){
        List<ChatRoomDto> result = new ArrayList<>(chatRoomDtoMap.values());
        Collections.reverse(result);
        return result;
    }

    public ChatRoomDto findRoomById(String id){
        return chatRoomDtoMap.get(id);
    }

    public ChatRoomDto createChatRoomDto(String name){
        ChatRoomDto chatRoom = ChatRoomDto.create(name);
        chatRoomDtoMap.put(chatRoom.getRoomId(),chatRoom);
        return chatRoom;
    }
}
