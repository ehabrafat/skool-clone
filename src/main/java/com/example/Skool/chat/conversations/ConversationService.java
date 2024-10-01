package com.example.Skool.chat.conversations;


import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.auth.services.UserCreatorService;
import com.example.Skool.chat.dtos.MessageErrorDto;
import com.example.Skool.chat.dtos.PrivateMessageResDto;
import com.example.Skool.chat.dtos.SendMessageDto;
import com.example.Skool.chat.messages.Message;
import com.example.Skool.chat.messages.MessageService;
import com.example.Skool.common.exceptions.SkoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserCreatorService userCreatorService;


    public Page<Message> getConversationMessages(int conversationId, Pageable pageable, Authentication authentication) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() ->
                new SkoolException("Conversation not found", this.getClass().toString(), HttpStatus.NOT_FOUND)
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        if(conversation.getMemberOne().getId() != userId && conversation.getMemberTwo().getId() != userId) {
            throw new SkoolException("Not member of the conversation", this.getClass().toString(), HttpStatus.FORBIDDEN);
        }
        return messageService.getMessagesByConversationId(conversationId, pageable);
    }

    public Page<Conversation> getConversations(Authentication authentication, Pageable pageable) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        return conversationRepository.getConversations(userId, pageable);
    }



    public Conversation createConversation(int senderId, int receiverId) {
        UserCreator sender = new UserCreator();;
        sender.setId(senderId);
        UserCreator receiver = new UserCreator();;
        receiver.setId(receiverId);
        Conversation conversation = new Conversation();
        conversation.setMemberOne(sender);
        conversation.setMemberTwo(receiver);
        return conversationRepository.save(conversation);
    }

    public Optional<Conversation> getByMemberOneIdAndMemberTwoId(int senderId, int receiverId) {
        return conversationRepository.getByMemberOneIdAndMemberTwoId(senderId, receiverId);
    }


    public void sendMessage(SimpMessageHeaderAccessor headerAccessor, SendMessageDto messageDto) {
        if(messageDto.getContent() == null || messageDto.getContent().isEmpty()) {
            MessageErrorDto messageErrorDto = MessageErrorDto.builder()
                    .message("Message content cannot be empty")
                    .status(HttpStatus.BAD_REQUEST.name())
                    .build();
            messagingTemplate.convertAndSend("/topic/error", messageErrorDto);
            return;
        }
        if(!userCreatorService.isExists(messageDto.getReceiverId())){
            MessageErrorDto messageErrorDto = MessageErrorDto.builder()
                    .message("Receiver Not found")
                    .status(HttpStatus.BAD_REQUEST.name())
                    .build();
            messagingTemplate.convertAndSend("/topic/error", messageErrorDto);
            return;
        }
        int senderId = (int) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        int receiverId = messageDto.getReceiverId();
        Conversation conversation = getByMemberOneIdAndMemberTwoId(senderId, receiverId)
                .orElseGet(() -> getByMemberOneIdAndMemberTwoId(receiverId, senderId)
                        .orElseGet(() -> createConversation(senderId, receiverId)));
        UserCreator sender = new UserCreator();
        sender.setId(senderId);
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setConversation(conversation);
        message.setSender(sender);
        messageService.save(message);
        messagingTemplate.convertAndSendToUser(String.valueOf(receiverId), "private",
                PrivateMessageResDto.builder()
                        .content(message.getContent())
                        .senderId(senderId)
                        .build());
    }

}
