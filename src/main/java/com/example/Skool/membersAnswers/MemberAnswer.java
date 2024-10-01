package com.example.Skool.membersAnswers;


import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.communityQuestions.CommunityQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members_answers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private CommunityQuestion question;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private UserCreator member;
}
