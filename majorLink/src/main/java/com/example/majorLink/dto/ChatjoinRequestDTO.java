package com.example.majorLink.dto;

import com.example.majorLink.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ChatjoinRequestDTO {
    private Long roomId;

}
