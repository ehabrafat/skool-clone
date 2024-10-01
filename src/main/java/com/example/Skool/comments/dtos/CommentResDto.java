package com.example.Skool.comments.dtos;

import com.example.Skool.auth.dtos.UserCreatorRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResDto {
    private int id;

    private String content;
}
