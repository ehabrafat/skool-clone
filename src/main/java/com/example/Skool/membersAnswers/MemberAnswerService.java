package com.example.Skool.membersAnswers;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.communities.dtos.QuestionAnswer;
import com.example.Skool.communityQuestions.CommunityQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAnswerService {

    private final MemberAnswerRepository memberAnswerRepository;

    public MemberAnswer createMemberAnswer(int memberId, QuestionAnswer answer) {
        MemberAnswer memberAnswer = new MemberAnswer();
        UserCreator user = new UserCreator();
        user.setId(memberId);
        memberAnswer.setMember(user);
        CommunityQuestion question = new CommunityQuestion();
        question.setId(answer.getQuestionId());
        memberAnswer.setQuestion(question);
        memberAnswer.setAnswerText(answer.getAnswer());
        return memberAnswerRepository.save(memberAnswer);
    }

    public Optional<MemberAnswer> getMemberAnswer(int memberId, int questionId) {
        return memberAnswerRepository.findByMemberIdAndQuestionId(memberId, questionId);
    }
}
