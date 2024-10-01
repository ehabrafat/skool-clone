package com.example.Skool.comments.types;

import lombok.Data;

import java.util.List;

@Data
public class CommentRepliesPage {
    private List<CommentReply> content;
    private int numberOfElements;
    private long hasMoreCommentsCount;
    private boolean hasNext;
    private boolean hasPrevious;
}
