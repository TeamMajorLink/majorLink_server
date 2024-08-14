package com.example.majorLink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatmessageResponseDTO {
        private Long id;
        private String content;
        private String senderUsername;
        private Long chatroomId;

}
