package com.example.Skool.common.mappers;


import com.example.Skool.chat.conversations.Conversation;
import com.example.Skool.chat.conversations.dtos.ConversationResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ConversationMapper {

    public ConversationResDto toResDto(Conversation conversation) {
        UserMapper userMapper = new UserMapper();
        return ConversationResDto.builder()
                .id(conversation.getId())
                .memberOne(userMapper.toUserResDto(conversation.getMemberOne()))
                .memberTwo(userMapper.toUserResDto(conversation.getMemberTwo()))
                .build();
    }
}
