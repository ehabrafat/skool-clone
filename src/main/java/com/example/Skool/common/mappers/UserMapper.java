package com.example.Skool.common.mappers;


import com.example.Skool.auth.dtos.RegisterUserDto;
import com.example.Skool.auth.dtos.UserCreatorRes;
import com.example.Skool.auth.entities.UserCreator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class UserMapper {

    public UserCreator toUserEntity(RegisterUserDto userDto) {
        return UserCreator.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public UserCreatorRes toUserResDto(UserCreator user) {
        return UserCreatorRes.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
