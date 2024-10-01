package com.example.Skool.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class RegisterUserDto{
    @NotBlank
    @Length(min = 4, message = "username must be at least 4 characters")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 6, message = "password must be at least 6 characters")
    private String password;

}
