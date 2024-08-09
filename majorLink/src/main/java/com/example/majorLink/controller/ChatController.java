package com.example.majorLink.controller;


import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.dto.ChatRoomRequestDTO;
import com.example.majorLink.dto.ChatRoomResponseDTO;
import com.example.majorLink.dto.ChatmessageResponseDTO;
import com.example.majorLink.repository.ChatRoomRepository;
import com.example.majorLink.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;


    //send message 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")
    public Map<String, Object> sendMessage(Map<String, Object> message) {
        UUID sender = UUID.fromString(message.get("sender").toString());
        String content = message.get("content").toString();
        Integer chatRoomIdInteger = (Integer) message.get("chatroom");
        long roomId = chatRoomIdInteger.longValue();

        // 로그 추가
        System.out.println("Received message: " + content);
        System.out.println("Sender UUID: " + sender);
        System.out.println("Room ID: " + roomId);

        ChatRoom chatRoom = chatService.getChatroomById(roomId);

        ChatMessage message1 = ChatMessage.builder()
                .content(content)
                .chatRoom(chatRoom)
                .build();

        // 로그 추가
        System.out.println("Saving message: " + message1);


        ChatMessage chat = chatService.saveChatMessage(message1, sender);

        Map<String, Object> result = new HashMap<>();

        result.put("id", chat.getId());
        result.put("name", chat.getSender().getUsername());
        result.put("content", chat.getContent());
        result.put("chatroom_id", chat.getChatRoom().getId());
        return result;
    }


    //채팅방 생성 컨트롤러
    @PostMapping("/chatrooms")
    public ChatRoom createChatroom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO) {
        return chatService.createChatroom(chatRoomRequestDTO.getName());
    }


    //채팅방 전체 조회 컨트롤러
    @GetMapping("/chatrooms")
    public List<ChatRoomResponseDTO> getAllChatRoom() {
        return chatService.getAllChatRoom().stream().map(chatRoom -> ChatRoomResponseDTO.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .build())
                .toList();
    }

    //채팅 기록 조회 컨트롤러
    @GetMapping("/chat/history/{roomId}")
    public List<ChatmessageResponseDTO> getChatHistory(@PathVariable Long roomId) {
        return chatService.getChatMessageById(roomId).stream().map(chatMessage -> ChatmessageResponseDTO.builder()
                .id(chatMessage.getId())
                .senderUsername(chatMessage.getSender().getUsername())
                .content(chatMessage.getContent())
                .chatroomId(chatMessage.getChatRoom().getId())
                .build())
                .toList();
    }






}
