package com.example.Skool.commentLikes.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentLikeResDto {

    private int id;

    private int userId;

    private int commentId;
}
