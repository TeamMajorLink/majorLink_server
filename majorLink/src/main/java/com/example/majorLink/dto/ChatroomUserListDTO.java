package com.example.majorLink.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ChatroomUserListDTO {
    private UUID userId;
    private String userName;
}
