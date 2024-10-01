package com.example.Skool.chat.messages.dtos;

import com.example.Skool.auth.dtos.UserCreatorRes;
import com.example.Skool.chat.messages.Message;
import com.example.Skool.common.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResDto {

    private int id;

    private String content;

    private UserCreatorRes sender;
}
