package com.example.Skool.common.mappers;

import com.example.Skool.commentLikes.CommentLike;
import com.example.Skool.commentLikes.dtos.CommentLikeResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class CommentLikeMapper {

    public CommentLikeResDto toResDto(CommentLike commentLike) {
        return CommentLikeResDto.builder()
                .id(commentLike.getId())
                .commentId(commentLike.getComment().getId())
                .userId(commentLike.getCreator().getId())
                .build();
    }
}
