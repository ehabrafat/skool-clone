package com.example.Skool.chat;

import com.example.Skool.chat.conversations.ConversationService;
import com.example.Skool.chat.dtos.SendMessageDto;
import com.example.Skool.chat.messages.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ConversationService conversationService;

    @MessageMapping("/messages")
    public void sendMessage(SimpMessageHeaderAccessor headerAccessor, @Payload SendMessageDto message) {
        conversationService.sendMessage(headerAccessor, message);
    }
}
