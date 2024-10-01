package com.example.Skool.auth.entities;


import com.example.Skool.chat.conversations.Conversation;
import com.example.Skool.commentLikes.CommentLike;
import com.example.Skool.comments.Comment;
import com.example.Skool.communities.Community;
import com.example.Skool.membersAnswers.MemberAnswer;
import com.example.Skool.memberships.Membership;
import com.example.Skool.postLikes.PostLike;
import com.example.Skool.posts.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private Date createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnore
    private List<Community> communities;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    @JsonIgnore
    private List<MemberAnswer> memberAnswers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    @JsonIgnore
    private List<Membership> memberships;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnore
    private List<PostLike> postLikes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnore
    private List<CommentLike> commentLikes;

}
