package com.example.Skool.chat.messages;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.auth.services.UserCreatorService;
import com.example.Skool.chat.conversations.Conversation;
import com.example.Skool.chat.conversations.ConversationService;
import com.example.Skool.chat.dtos.MessageErrorDto;
import com.example.Skool.chat.dtos.PrivateMessageResDto;
import com.example.Skool.chat.dtos.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }
    public Page<Message> getMessagesByConversationId(int conversationId, Pageable pageable) {
        return messageRepository.getMessagesByConversationId(conversationId, pageable);
    }
}
