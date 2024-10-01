package com.example.Skool.commentLikes;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.comments.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    public CommentLike save(int userId, int CommentId) {
        UserCreator creator = new UserCreator();
        creator.setId(userId);
        Comment comment = new Comment();
        comment.setId(CommentId);
        CommentLike commentLike = new CommentLike();
        commentLike.setComment(comment);
        commentLike.setCreator(creator);
        return commentLikeRepository.save(commentLike);
    }

    public Page<CommentLike> getAll(int commentId, Pageable pageable) {
        return commentLikeRepository.findAllByCommentId(commentId, pageable);
    }

    public int getLikesCount(int commentId) {
        return commentLikeRepository.getLikesCount(commentId);
    }
}
