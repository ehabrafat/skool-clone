package com.example.Skool.auth.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    @NotNull(message = "username cannot be empty")
    private String username;
    @NotNull(message = "password cannot be empty")
    private String password;
}
