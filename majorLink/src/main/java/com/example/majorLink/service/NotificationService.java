package com.example.majorLink.service;

import com.example.majorLink.domain.*;
import com.example.majorLink.dto.response.NotificationResponse;
import com.example.majorLink.repository.EmitterRepository;
import com.example.majorLink.repository.EmitterRepositoryImpl;
import com.example.majorLink.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(User user, String lastEventId) {
        // 토큰을 고유 식별자로 사용하여 emitterId를 생성합니다.
        String emitterId = user.getId() + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> {
            System.out.println("SSE Connection Completed, emitterId: " + emitterId);
            emitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            System.out.println("SSE Connection Timed Out, emitterId: " + emitterId);
            emitterRepository.deleteById(emitterId);
        });

        sendToClient(emitter, emitterId, "EventStream Create. [userId = " + user.getId() + "]");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByUserId(user.getId());
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    public void send(User sender, Lecture lecture, String content) {
        Notification notification = notificationRepository.save(createNotification(sender, lecture, content));
        String userId = String.valueOf(sender.getId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(UUID.fromString(userId));
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, key, NotificationResponse.builder()
                            .id(notification.getId())
                            .content(String.valueOf(notification.getContent()))
                            .url(String.valueOf(notification.getUrl()))
                            .sender(sender.getNickname())
                            .receiver(lecture.getUser().getNickname())
                            .createdAt(String.valueOf(notification.getCreatedAt()))
                            .build());
                }
        );
    }

    private Notification createNotification(User sender, Lecture lecture, String content) {
        String url = "/lecture/" + lecture.getId() + "/details";

        return Notification.builder()
                .sender(sender)
                .receiver(lecture.getUser())
                .content(content)
                .lecture(lecture)
                .url(url)
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
}
