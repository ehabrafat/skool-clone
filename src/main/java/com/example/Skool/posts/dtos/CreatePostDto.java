package com.example.Skool.posts.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostDto {
    @NotNull(message =  "post title is required")
    private String title;

    @NotNull(message =  "post content is required")
    private String content;
}
