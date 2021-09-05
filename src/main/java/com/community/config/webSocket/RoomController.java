package com.community.config.webSocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
@Controller(value = "/chat")
public class RoomController {

    private final ChatRoomRepository chatRepository;

    //채팅방 목록 조회
    @ResponseBody
    @GetMapping(value = "/rooms")
    public List<ChatRoomDto> rooms(){
        log.info("# All Chat Rooms");
        return chatRepository.findAllRooms();
    }

    //채팅방 개설
    @ResponseBody
    @PostMapping(value = "/room")
    public List<ChatRoomDto> create(@RequestParam String name){
        log.info("# Create Chat Room , name: " + name);
        ChatRoomDto.create(name);
        return chatRepository.findAllRooms();
    }

    //채팅방 조회
    @GetMapping("/room")
    public ChatRoomDto getRoom(String roomId){
        log.info("# get Chat Room, roomID : " + roomId);
        return chatRepository.findRoomById(roomId);
    }
}