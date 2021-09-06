package com.community.config.webSocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
@Controller(value = "/chat")
public class RoomController {

    private final ChatRoomRepository chatRepository;

    @PostConstruct
    public void init(){
        ChatRoomDto test_room1 = chatRepository.createChatRoomDTO("test room1");
        System.out.println("test_room1 = " + test_room1.getRoomId());
        ChatRoomDto test_room2 = chatRepository.createChatRoomDTO("test room2");
        System.out.println("test_room2 = " + test_room2.getRoomId());
        ChatRoomDto test_room3 = chatRepository.createChatRoomDTO("test room3");
        System.out.println("test_room3 = " + test_room3.getRoomId());
    }

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
        chatRepository.createChatRoomDTO(name);
        return chatRepository.findAllRooms();
    }

    //채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDto getRoom(@PathVariable String roomId){
        log.info("# get Chat Room, roomID : " + roomId);
        return chatRepository.findRoomById(roomId);
    }
}