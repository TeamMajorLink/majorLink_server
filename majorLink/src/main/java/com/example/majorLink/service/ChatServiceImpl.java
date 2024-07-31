package com.example.majorLink.service;

import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.repository.ChatMessageRepository;
import com.example.majorLink.repository.ChatRoomRepository;
import com.example.majorLink.repository.UserChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserChatRepository userChatRepository;


    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage, Long sender) {
        return null;
    }

    @Override
    public List<ChatMessage> getChatMessageById(Long roomId) {
        return null;
    }

    @Override
    public List<ChatRoom> getChatroomListById(Long userId) {
        return null;
    }

    @Override
    public List<String> getChatroomUsernameById(Long roodId) {
        return null;
    }

    @Override
    public ChatRoom createChatroom(String roomName) {
        return null;
    }

    @Override
    public Long joinRoom(Long roomId, Long userId) {
        return null;
    }
}
