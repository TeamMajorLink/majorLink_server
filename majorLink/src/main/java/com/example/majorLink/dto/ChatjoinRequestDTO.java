package com.example.majorLink.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ChatjoinRequestDTO {
    private Long roomId;
    private UUID userId;
}
