package com.example.majorLink.controller;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.response.NotificationResponse;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.global.jwt.JwtService;
import com.example.majorLink.repository.LectureRepository;
import com.example.majorLink.service.NotificationService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final LectureRepository lectureRepository;
    private final JwtService jwtService;

    /**
     * 알림을 위한 구독 API
     * [GET] /notification/subscribe/{X-AUTH-TOKEN}
     * @param lastEventId
     * @param token
     * @return
     */
    // Last-Event-ID는 SSE 연결이 끊어졌을 경우, 클라이언트가 수신한 마지막 데이터 ID 값을 의미, 항상 존재 X -> false
    @GetMapping(value = "/subscribe/{X-AUTH-TOKEN}", produces = "text/event-stream")
    public SseEmitter subscribe(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
            @PathVariable("X-AUTH-TOKEN") String token
    ) {

        // 토큰 유효성 검사
        if (!jwtService.checkValidationToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 사용자 UUID 추출
        Claims claims = jwtService.getClaims(token);
        String userId = claims.get("uuid", String.class);

        // UUID 변환 및 검증
        UUID userUuid;
        try {
            userUuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 UUID입니다.", e);
        }

        // notificationService를 통해 사용자 구독 처리
        return notificationService.subscribe(userUuid, lastEventId);
    }


    // 수강 신청 시 튜터에게 알림 전달

    /**
     * 알림 전송 API
     * [POST] /notification/{lectureId}
     * @param lectureId
     * @param authUser
     * @return
     */
    @PostMapping("/{lectureId}")
    public ResponseEntity<?> sendNotification(@PathVariable(name = "lectureId") Long lectureId,
                                              @AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        String msg = user.getNickname() + " 님으로 부터 수업 신청이 왔습니다.";
        notificationService.send(user, lectureId, msg);
        return ResponseEntity.status(HttpStatus.OK)
                .build();

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
