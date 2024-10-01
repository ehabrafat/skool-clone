package com.example.Skool.common.mappers;


import com.example.Skool.communities.dtos.CommunityResDto;
import com.example.Skool.communityQuestions.CommunityQuestion;
import com.example.Skool.communityQuestions.dtos.CommunityQuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class CommunityQuestionMapper {

    public CommunityQuestionDto toCommunityQuestionDto(CommunityQuestion communityQuestion) {
        return CommunityQuestionDto.builder().question(communityQuestion.getQuestionText()).build();
    }
}
