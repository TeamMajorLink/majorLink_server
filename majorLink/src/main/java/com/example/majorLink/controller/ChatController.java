package com.example.majorLink.controller;

import com.example.majorLink.converter.ChatRoomConverter;
import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.dto.ChatRoomRequestDTO;
import com.example.majorLink.dto.ChatRoomResponseDTO;
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

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;


    //send message 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")
    public Map<String, Object> sendMessage(Principal principal, Map<String, Object> message) {
        Long sender = Long.parseLong(message.get("sender").toString());
        String content = message.get("content").toString();
        Integer chatRoomIdInteger = (Integer) message.get("chatroom");
        long roomId = chatRoomIdInteger.longValue();

        ChatRoom chatRoom = chatService.getChatroomById(roomId);

        ChatMessage message1 = ChatMessage.builder()
                .content(content)
                .chatRoom(chatRoom)
                .build();
        System.out.println(message1);

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
        return chatService.getAllChatRoom().stream().map(ChatRoomConverter::toChatRoomResponseDTO).toList();
    }

    //채팅 기록 조회 컨트롤러
    @GetMapping("/chat/history/{roomId}")
    public List<ChatMessage> getChatHistory(@PathVariable Long roomId) {
        return chatService.getChatMessageById(roomId);
    }




}
