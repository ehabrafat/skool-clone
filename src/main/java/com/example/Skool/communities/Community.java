package com.example.Skool.communities;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.communityCategories.CommunityCategory;
import com.example.Skool.communityQuestions.CommunityQuestion;
import com.example.Skool.memberships.Membership;
import com.example.Skool.posts.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "communities")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String imgUrl;

    @Column()
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunityVisibility visibility;

    @Column(nullable = false)
    private int costPerMonth;

    @Column(columnDefinition = "TEXT")
    private String social;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private CommunityCategory category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    @JsonManagedReference
    private UserCreator creator;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "community")
    private List<CommunityQuestion> communityQuestions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "community")
    @JsonBackReference
    private List<Membership> memberships;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "community")
    @JsonBackReference
    private List<Post> posts;
}
