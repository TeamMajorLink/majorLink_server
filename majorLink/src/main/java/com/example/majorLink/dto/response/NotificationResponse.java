package com.example.majorLink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {
    private Long id;
    private String content;
    private String url;
    private Boolean isRead;
    private String createdAt;
}
