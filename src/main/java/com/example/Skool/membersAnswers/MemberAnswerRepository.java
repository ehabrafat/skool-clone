package com.example.Skool.membersAnswers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberAnswerRepository extends JpaRepository<MemberAnswer, Integer> {

    Optional<MemberAnswer> findByMemberIdAndQuestionId(int memberId, int questionId);
}
