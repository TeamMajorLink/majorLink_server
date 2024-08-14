package com.example.majorLink.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomResponseDTO {
    private Long id;
    private String name;
}
