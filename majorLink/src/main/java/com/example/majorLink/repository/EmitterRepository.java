package com.example.majorLink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    // 해당 유저와 관련된 모든 emitter를 찾는다
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(UUID userId);
    // 해당 유저와 관련된 모든 이벤트를 찾는다.
    Map<String, Object> findAllEventCacheStartWithByUserId(UUID userId);

    void deleteById(String emitterId);
}
