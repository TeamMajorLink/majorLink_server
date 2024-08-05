package com.example.majorLink.controller;

import com.example.majorLink.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;


    //send message 컨트롤러
    @MessageMapping("/chat.message")
    @SendTo("/topic/public")


}
