package com.example.Skool.common.mappers;

import com.example.Skool.comments.Comment;
import com.example.Skool.comments.dtos.CommentResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class CommentMapper {

    public CommentResDto toResDto(Comment comment) {
        return CommentResDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
}
