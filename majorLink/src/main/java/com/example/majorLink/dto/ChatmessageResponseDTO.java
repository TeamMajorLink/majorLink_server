package com.example.majorLink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

        @JsonProperty("name")
        private String name;

        private Long chatroomId;

}
