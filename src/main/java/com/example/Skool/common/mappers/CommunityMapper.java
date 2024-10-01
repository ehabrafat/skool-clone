package com.example.Skool.common.mappers;

import com.example.Skool.communities.Community;
import com.example.Skool.communities.dtos.CommunityResDto;
import org.mapstruct.Mapper;
import java.util.ArrayList;
import java.util.Arrays;

@Mapper(componentModel = "spring")
public class CommunityMapper {


    public CommunityResDto toCommunityResponseDto(Community community){
        UserMapper userMapper = new UserMapper();
        CommunityQuestionMapper communityQuestionMapper = new CommunityQuestionMapper();
        return CommunityResDto.builder()
                .id(community.getId())
                .title(community.getTitle())
                .description(community.getDescription())
                .visibility(community.getVisibility().toString())
                .costPerMonth(community.getCostPerMonth())
                .categoryId(community.getCategory().getId())
                .creator(userMapper.toUserResDto(community.getCreator()))
                .social(community.getSocial().isEmpty() ? new ArrayList<>() : Arrays.stream(community.getSocial().split(",")).toList())
                .questions(community.getCommunityQuestions().stream().map(communityQuestionMapper::toCommunityQuestionDto).toList())
                .build();
    }
}
