package com.example.Skool.common.mappers;


import com.example.Skool.postLikes.PostLike;
import com.example.Skool.postLikes.dtos.PostLikeResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class PostLikeMapper {

    public PostLikeResDto toResDto(PostLike postLike) {
        return PostLikeResDto.builder()
                .id(postLike.getId())
                .postId(postLike.getPost().getId())
                .userId(postLike.getCreator().getId())
                .build();
    }
}
