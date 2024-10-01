package com.example.Skool.common.mappers;

import com.example.Skool.posts.Post;
import com.example.Skool.posts.dtos.PostResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class PostMapper {

    public PostResDto toResDto(Post post) {
        return PostResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .creatorId(post.getCreator().getId())
                .build();
    }
}
