package com.example.Skool.postLikes.dtos;


import com.example.Skool.auth.dtos.UserCreatorRes;
import com.example.Skool.auth.entities.UserCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeResDto {

    private int id;

    private int postId;

    private int userId;
}
