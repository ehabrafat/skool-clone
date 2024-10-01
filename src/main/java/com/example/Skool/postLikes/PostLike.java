package com.example.Skool.postLikes;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.posts.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "post_likes", uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "creator_id"})})
@Data
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserCreator creator;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private Date updatedAt;
}
