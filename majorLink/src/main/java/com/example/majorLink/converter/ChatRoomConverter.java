package com.example.majorLink.converter;

import com.example.majorLink.domain.ChatRoom;
import com.example.majorLink.dto.ChatRoomResponseDTO;

public class ChatRoomConverter {

    public static ChatRoomResponseDTO toChatRoomResponseDTO(ChatRoom chatRoom) {
        return ChatRoomResponseDTO.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .build();
    }
}
