package com.example.Skool.comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByPostIdAndReplyToCommentId(int postId, Integer replyToCommentId, Pageable pageable);

    @Query("SELECT COUNT(c.id) FROM Comment c WHERE c.post.id = :postId AND c.replyToComment.id = :replyToCommentId")
    int getRepliesCount(int postId, int replyToCommentId);
}
