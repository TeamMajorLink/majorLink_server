package com.example.majorLink.service;

import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.domain.User;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    //메세지 저장
    ChatMessage saveChatMessage(ChatMessage chatMessage, UUID sender);

    //채팅방 메세지 전체 조회
    List<ChatMessage> getChatMessageById(Long roomId);

    //채팅방 목록 조회
    List<ChatRoom> getChatroomListById(Long userId);


    //채팅방 내의 유저 전체 조회
    List<User> getUserById(Long roomId);

    //채팅방 생성
    ChatRoom createChatroom(String roomName);

    //채팅방 입장
    String joinRoom(Long roomId, UUID userId);

    ChatRoom getChatroomById(Long roomId);

    //채팅방 전체조회
    List<ChatRoom> getAllChatRoom();


}
