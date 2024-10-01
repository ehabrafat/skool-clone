package com.example.Skool.chat.conversations;

import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.chat.conversations.dtos.ConversationResDto;
import com.example.Skool.chat.messages.Message;
import com.example.Skool.chat.messages.dtos.MessageResDto;
import com.example.Skool.common.dtos.PageResponseDto;
import com.example.Skool.common.mappers.ConversationMapper;
import com.example.Skool.common.mappers.HelperMapper;
import com.example.Skool.common.mappers.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationMapper conversationMapper;
    private final HelperMapper helperMapper;
    private final MessageMapper messageMapper;

    @GetMapping("/conversations")
    public PageResponseDto<ConversationResDto> getConversations(
            @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            Authentication auth) {
        Page<Conversation> conversations = conversationService.getConversations(auth, PageRequest.of(pageNum, pageSize));
        List<ConversationResDto> content = conversations.getContent().stream().map(conversationMapper::toResDto).toList();
        return helperMapper.toPageResponseDto(conversations, content);
    }

    @GetMapping("/conversations/{id}/messages")
    public PageResponseDto<MessageResDto> getConversationMessages(
            @PathVariable int id,
            @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            Authentication auth) {
        Page<Message> messages = conversationService.getConversationMessages(id, PageRequest.of(pageNum, pageSize), auth);
        List<MessageResDto> content = messages.getContent().stream().map(messageMapper::toResDto).toList();
        return helperMapper.toPageResponseDto(messages, content);
    }
}
