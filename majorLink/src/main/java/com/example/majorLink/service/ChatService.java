package com.example.majorLink.service;

import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;

import java.util.List;

public interface ChatService {

    //메세지 저장
    ChatMessage saveChatMessage(ChatMessage chatMessage, Long sender);

    //채팅방 메세지 전체 조회
    List<ChatMessage> getChatMessageById(Long roomId);

    //채팅방 목록 조회
    List<ChatRoom> getChatroomListById(Long userId);

    //채팅방 내의 유저 닉네임 전체 조회
    List<String> getChatroomUsernameById(Long roodId);

    //채팅방 생성
    ChatRoom createChatroom(String roomName);

    //채팅방 입장
    Long joinRoom(Long roomId, Long userId);
}
