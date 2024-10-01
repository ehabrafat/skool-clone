package com.example.Skool.communities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionAnswer {
    private int questionId;
    private String answer;
}
