package com.example.Skool.communities.dtos;

import lombok.Data;

import java.util.List;

@Data
public class JoinCommunityDto {
    private List<QuestionAnswer> answers;
}

