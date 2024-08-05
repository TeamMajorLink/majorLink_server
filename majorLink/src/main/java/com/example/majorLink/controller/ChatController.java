package com.example.majorLink.controller;

import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.repository.ChatRoomRepository;
import com.example.majorLink.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
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
        result.put("name", chat.getSender().getUserName());
        result.put("content", chat.getContent());
        result.put("chatroom_id", chat.getChatRoom().getId());
        return result;
    }




}
