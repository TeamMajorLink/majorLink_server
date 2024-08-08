package com.example.majorLink.service;

import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.mapping.UserChat;
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


    //메세지 저장
    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage, Long sender) {
        //아직 유저 레포지토리 없어서 임시 작성
        //User user = userRepository.findById(sender).orElseThroew 예외처리
        //chatMessage.setUser(user);
        //return chatMessageRepository.save(chatMessage);
        return null;

    }

    //방id로 메세지 조회
    @Override
    public List<ChatMessage> getChatMessageById(Long roomId) {
        return chatMessageRepository.findByChatRoomId(roomId);
    }


    //채팅방 전체 조회
    @Override
    public List<ChatRoom> getChatroomListById(Long userId) {
        return chatRoomRepository.findAll();
    }

    @Override
    public List<String> getChatroomUsernameById(Long roodId) {
        return null;
    }


    //채팅방 생성
    @Override
    public ChatRoom createChatroom(String roomName) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(roomName)
                .build();
        return chatRoomRepository.save(chatRoom);
    }


    //채팅방 입장
    @Override
    public Long joinRoom(Long roomId, Long userId) {
        //아직 유저 레포지토리 없어서 임시작성
//        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("찾는 채팅방이 없습니다"));
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("찾는 유저가 없습니다"));
//        UserChat userChat = UserChat.builder().chatRoom(chatRoom).user(user).build();
//        return userChat.getUser().getId();
        return null;
    }

    @Override
    public ChatRoom getChatroomById(Long roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지않습니다."));
    }

    @Override
    public List<ChatRoom> getAllChatRoom() {
        return chatRoomRepository.findAll();
    }

}


