package com.example.Skool.communityQuestions;

import com.example.Skool.common.exceptions.SkoolException;
import com.example.Skool.communities.Community;
import com.example.Skool.communities.CommunityRepository;
import com.example.Skool.communityQuestions.dtos.CommunityQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityQuestionService {
    private final CommunityQuestionRepository communityQuestionRepository;
    private final CommunityRepository communityRepository;

    public List<CommunityQuestion> getCommunityQuestions(int communityId) {
        return this.communityQuestionRepository.findByCommunityId(communityId);
    }

    public CommunityQuestion addCommunityQuestion(int communityId, CommunityQuestionDto communityQuestionDto) {
       Community community = new Community();
       community.setId(communityId);
       CommunityQuestion communityQuestion = new CommunityQuestion();
       communityQuestion.setCommunity(community);
       communityQuestion.setQuestionText(communityQuestionDto.getQuestion());
       return this.communityQuestionRepository.save(communityQuestion);
    }
}
