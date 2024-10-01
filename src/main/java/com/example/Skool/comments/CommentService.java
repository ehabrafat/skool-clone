package com.example.Skool.comments;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.comments.dtos.CreateCommentDto;
import com.example.Skool.posts.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CommentService {
    private final CommentRepository commentRepository;


    public Comment save(int userId, int postId, CreateCommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        if(commentDto.getReplyToCommentId() != null){
            Comment replyComment = new Comment();
            replyComment.setId(commentDto.getReplyToCommentId());
            comment.setReplyToComment(replyComment);
        }
        UserCreator creator = new UserCreator();
        creator.setId(userId);
        comment.setCreator(creator);
        Post post = new Post();
        post.setId(postId);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Page<Comment> getComments(int postId, Integer replyToCommentId, Pageable pageable) {
        return commentRepository.findAllByPostIdAndReplyToCommentId(postId, replyToCommentId, pageable);
    }

    public int getRepliesCount(int postId, Integer replyToCommentId) {
        return commentRepository.getRepliesCount(postId, replyToCommentId);
    }

    public boolean isExists(int commentId){
        return commentRepository.existsById(commentId);
    }

    public Optional<Comment> getById(int commentId){
        return commentRepository.findById(commentId);
    }

}
