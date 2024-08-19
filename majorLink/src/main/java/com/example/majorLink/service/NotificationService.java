package com.example.majorLink.service;

import com.example.majorLink.domain.*;
import com.example.majorLink.dto.response.NotificationResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {
    SseEmitter subscribe(User user, String lastEventId);
    void send(User sender, Lecture lecture, String content);

    // 전체 알림 조회
    List<NotificationResponse> getNotificationList(User user);
}
