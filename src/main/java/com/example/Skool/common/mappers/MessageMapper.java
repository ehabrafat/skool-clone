package com.example.Skool.common.mappers;


import com.example.Skool.chat.messages.Message;
import com.example.Skool.chat.messages.dtos.MessageResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class MessageMapper {

    public MessageResDto toResDto(Message message) {
        UserMapper userMapper = new UserMapper();
        return MessageResDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .sender(userMapper.toUserResDto(message.getSender()))
                .build();
    }

}
