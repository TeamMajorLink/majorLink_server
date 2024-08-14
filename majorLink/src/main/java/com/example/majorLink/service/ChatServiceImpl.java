package com.example.majorLink.service;

import com.example.majorLink.domain.ChatMessage;
import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.mapping.UserChat;
import com.example.majorLink.repository.ChatMessageRepository;
import com.example.majorLink.repository.ChatRoomRepository;
import com.example.majorLink.repository.UserChatRepository;
import com.example.majorLink.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserChatRepository userChatRepository;
    private final UserRepository userRepository;


    //메세지 저장
    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage, UUID sender) {
        User user = userRepository.findById(sender).orElseThrow(() -> new RuntimeException("존재하지않는 유저입니다")); //예외처리 부분 추후 수정
        // 로그 추가
        System.out.println("User found: " + user);

        chatMessage.setUser(user);

        // 로그 추가
        System.out.println("Saving chat message: " + chatMessage);


        return chatMessageRepository.save(chatMessage);

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

    //채팅방 내의 유저 전체 조회
    @Override
    public List<User> getUserById(Long roomId) {
        return userChatRepository.findByChatRoomId(roomId).stream()
                .map(UserChat::getUser) //Userchat에서 user 추출
                .collect(Collectors.toList()); //List<User>로 변환
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
    public String joinRoom(Long roomId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저 입니다"));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방입니다"));
        UserChat userChat = UserChat.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        userChatRepository.save(userChat);
        return userChat.getUser().getUsername()+ "님이" + userChat.getChatRoom().getId() + "방에 입장하셨습니다";

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


