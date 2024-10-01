package com.example.Skool.communityQuestions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityQuestionRepository extends JpaRepository<CommunityQuestion, Integer> {

    List<CommunityQuestion> findByCommunityId(int id);
}
