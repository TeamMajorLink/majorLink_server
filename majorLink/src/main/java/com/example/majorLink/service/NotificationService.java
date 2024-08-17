package com.example.majorLink.service;

import com.example.majorLink.domain.*;
import com.example.majorLink.dto.response.NotificationResponse;
import com.example.majorLink.repository.EmitterRepository;
import com.example.majorLink.repository.EmitterRepositoryImpl;
import com.example.majorLink.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public SseEmitter subscribe(UUID userId, String lastEventId) {
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

    public void send(User receiver, ProfileCard lecture, String content) {
        Notification notification = notificationRepository.save(createNotification(receiver, lecture, content));
        String userId = String.valueOf(receiver.getId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(UUID.fromString(userId));
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, key, NotificationResponse.builder()
                            .id(notification.getId())
                            .content(String.valueOf(notification.getContent()))
                            .url(String.valueOf(notification.getUrl()))
                            .isRead(notification.getIsRead())
                            .build());
                }
        );
    }

    private Notification createNotification(User receiver, ProfileCard lecture, String content) {
        RelatedUrl relatedUrl = new RelatedUrl();
        relatedUrl.setUrl("/lecture/" + lecture.getId());

        return Notification.builder()
                .receiver(receiver)
                .content(content)
                .lecture(lecture)
                .url(relatedUrl)
                .isRead(false)
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
