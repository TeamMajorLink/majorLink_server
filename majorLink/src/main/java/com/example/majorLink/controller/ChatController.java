package com.example.majorLink.controller;


import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.*;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.repository.UserRepository;
import com.example.majorLink.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    //send message 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")
    public Map<String, Object> sendMessage(Map<String, Object> message) {
        UUID sender = UUID.fromString(message.get("sender").toString());
//        User user = authUser.getUser();
//        String username = user.getUsername();

        User user = userRepository.findById(sender).orElseThrow(() ->
                new IllegalArgumentException("유저를 찾을 수 없습니다: " + sender));

        String content = message.get("content").toString();
        Integer chatRoomIdInteger = (Integer) message.get("chatroom");
        long roomId = chatRoomIdInteger.longValue();

        // 로그 추가
        System.out.println("Received message: " + content);
        System.out.println("username: " + user.getUsername());
        System.out.println("Room ID: " + roomId);

        ChatRoom chatRoom = chatService.getChatroomById(roomId);

        ChatMessage message1 = ChatMessage.builder()
                .content(content)
                .chatRoom(chatRoom)
                .sender(user)
                .build();

        // 로그 추가
        System.out.println("Saving message: " + message1);


        ChatMessage chat = chatService.saveChatMessage(message1, user);

        Map<String, Object> result = new HashMap<>();

        result.put("id", chat.getId());
        result.put("name", chat.getSender().getUsername());
        result.put("content", chat.getContent());
        result.put("chatroom_id", chat.getChatRoom().getId());
        return result;
    }


    //채팅방 생성 컨트롤러
    @PostMapping("/chatrooms")
    public ChatRoom createChatroom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO, @AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        return chatService.createChatroom(chatRoomRequestDTO.getName(), user);
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
                .name(chatMessage.getSender().getUsername())
                .content(chatMessage.getContent())
                .chatroomId(chatMessage.getChatRoom().getId())
                .build())
                .toList();
    }

    //채팅방 입장 컨트롤러
    @PostMapping("/chat/join/{roomId}")
    public String joinRoom(@PathVariable Long roomId, @AuthenticationPrincipal AuthUser authUser) {
        return chatService.joinRoom(roomId, authUser.getUser());
    }

    //채팅방별 입장한 유저 조회
    @GetMapping("/chatroom/user/{roomId}")
    public List<ChatroomUserListDTO> getUserList(@PathVariable Long roomId) {
        return chatService.getUserById(roomId).stream().map(user -> ChatroomUserListDTO.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .build())
                .toList();
    }








}
