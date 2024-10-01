package com.example.Skool.comments.types;

import lombok.Data;

@Data
public class CommentWithReplies {
    private int id;
    private String content;
    private boolean edited;
    private int likes;
    private CommentRepliesPage replies;
}
