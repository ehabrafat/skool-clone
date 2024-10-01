package com.example.Skool.communityQuestions;


import com.example.Skool.communities.Community;
import com.example.Skool.membersAnswers.MemberAnswer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "community_questions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String questionText;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @JsonIgnore
    private List<MemberAnswer> answers;

    @ManyToOne()
    @JoinColumn(name = "community_id")
    @JsonIgnore
    private Community community;
}
