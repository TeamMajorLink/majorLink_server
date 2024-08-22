package com.example.majorLink.service;

import com.example.majorLink.domain.*;
import com.example.majorLink.dto.response.NotificationResponse;
import com.example.majorLink.repository.EmitterRepository;
import com.example.majorLink.repository.EmitterRepositoryImpl;
import com.example.majorLink.repository.LectureRepository;
import com.example.majorLink.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private static final Long DEFAULT_TIMEOUT = 5L * 1000 * 60 * 60; // 5시간
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;
    private final LectureRepository lectureRepository;

    @Override
    public SseEmitter subscribe(UUID userId, String lastEventId) {
        // 토큰을 고유 식별자로 사용하여 emitterId를 생성합니다.
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> {
            System.out.println("SSE Connection Completed, emitterId: " + emitterId);
            emitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            System.out.println("SSE Connection Timed Out, emitterId: " + emitterId);
            emitterRepository.deleteById(emitterId);
        });

        sendToClient(emitter, emitterId, "EventStream Create. [userId = " + userId + "]");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByUserId(userId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    @Override
    public void send(User sender, Long lectureId, String content) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        Notification notification = notificationRepository.save(createNotification(sender, lectureId, content));
        UUID receiverId = lecture.getUser().getId();

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);       // 알림 받는 사람 아이디
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, key, NotificationResponse.builder()
                            .id(notification.getId())
                            .content(String.valueOf(notification.getContent()))
                            .lectureId(String.valueOf(lectureId))
                            .sender(sender.getNickname())
                            .receiver(lecture.getUser().getNickname())
                            .createdAt(String.valueOf(notification.getCreatedAt()))
                            .build());
                }
        );
    }

    private Notification createNotification(User sender, Long lectureId, String content) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("강의 정보가 없습니다."));

        return Notification.builder()
                .sender(sender)
                .receiver(lecture.getUser())
                .content(content)
                .lecture(lecture)
                .build();
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            String jsonData = new ObjectMapper().writeValueAsString(data);
            System.out.println("Sending Data to Client, emitterId: " + emitterId + ", data: " + jsonData);
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(jsonData));
        } catch (IOException exception) {
            System.err.println("Failed to send data, emitterId: " + emitterId + ", exception: " + exception.getMessage());
            emitterRepository.deleteById(emitterId);
            throw new RuntimeException("Failed to send data to client.", exception);
        }
    }

    // 전체 알림 조회
    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationList(User user) {
        List<Notification> notifications = notificationRepository.findAllByReceiver(user);
        return notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .sender(notification.getSender().getNickname())
                        .receiver(notification.getReceiver().getNickname())
                        .content(notification.getContent())
                        .lectureId(String.valueOf(notification.getLecture().getId()))
                        .createdAt(String.valueOf(notification.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());


    }
}
