package com.example.Skool.comments.types;

import lombok.Data;

import java.util.List;

@Data
public class CommentWithRepliesPage {
    private List<CommentWithReplies> content;
    private int numberOfElements;
    private long hasMoreCommentsCount;
    private boolean hasNext;
    private boolean hasPrevious;
}
