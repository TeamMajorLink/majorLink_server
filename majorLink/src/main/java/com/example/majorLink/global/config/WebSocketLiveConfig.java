package com.example.majorLink.global.config;

import com.example.majorLink.handler.VideoChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketLiveConfig implements WebSocketConfigurer {

    //화상채팅
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new VideoChatWebSocketHandler(), "/ws/video-chat").setAllowedOrigins("*");

    }
}
