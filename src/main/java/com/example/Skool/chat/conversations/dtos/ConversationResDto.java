package com.example.Skool.chat.conversations.dtos;

import com.example.Skool.auth.dtos.UserCreatorRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversationResDto {
    private int id;

    private UserCreatorRes memberOne;

    private UserCreatorRes memberTwo;
}
