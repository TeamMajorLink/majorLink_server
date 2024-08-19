package com.example.majorLink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {
    private Long id;
    private String receiver;
    private String sender;
    private String content;
    private String url;
    private String createdAt;
}
