package com.example.Skool.communities.dtos;

import com.example.Skool.auth.dtos.UserCreatorRes;
import com.example.Skool.communityQuestions.CommunityQuestion;
import com.example.Skool.communityQuestions.dtos.CommunityQuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResDto {

    private int id;

    private String title;

    private String description;

    private String visibility;

    private int costPerMonth;

    private int categoryId;

    private int totalNumberOfMembers;

    private List<String> social = new ArrayList<>();

    private List<CommunityQuestionDto> questions = new ArrayList<>();

    private UserCreatorRes creator;
}
