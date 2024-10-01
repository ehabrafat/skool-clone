package com.example.Skool.commentLikes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {

    Page<CommentLike> findAllByCommentId(int commentId, Pageable pageable);

    @Query("SELECT COUNT(c.id) FROM CommentLike c WHERE c.comment.id = :commentId")
    int getLikesCount(int commentId);
}
