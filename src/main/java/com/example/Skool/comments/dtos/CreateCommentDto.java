package com.example.Skool.comments.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentDto {

    @NotNull(message = "content is required")
    @NotEmpty(message = "content cannot be empty")
    private String content;

    private Integer replyToCommentId;
}
