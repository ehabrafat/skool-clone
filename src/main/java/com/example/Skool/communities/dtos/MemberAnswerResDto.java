package com.example.Skool.communities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberAnswerResDto {
    private int questionId;
    private String question;
    private String answer;
}
