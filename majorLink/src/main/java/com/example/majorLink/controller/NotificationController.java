package com.example.majorLink.controller;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.response.NotificationResponse;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    // Last-Event-ID는 SSE 연결이 끊어졌을 경우, 클라이언트가 수신한 마지막 데이터 ID 값을 의미, 항상 존재 X -> false

    /**
     * 알림을 위한 구독 API
     * [GET] /notification/subscribe
     * @param authUser
     * @param lastEventId
     * @return
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        User user = authUser.getUser();
        return notificationService.subscribe(user, lastEventId);
    }

    /**
     * 알림 전체 조회
     * [GET] /notification
     * @param authUser
     * @return
     */
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotificationList(
            @AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();

        List<NotificationResponse> notificationResponse = notificationService.getNotificationList(user);

        return ResponseEntity.status(HttpStatus.OK).body(notificationResponse);
    }
}
