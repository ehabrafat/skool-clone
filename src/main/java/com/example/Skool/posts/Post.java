package com.example.Skool.posts;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.comments.Comment;
import com.example.Skool.communities.Community;
import com.example.Skool.postLikes.PostLike;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean pinned;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private UserCreator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Community community;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "post")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostLike> postLikes;


    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private Date updatedAt;

}
