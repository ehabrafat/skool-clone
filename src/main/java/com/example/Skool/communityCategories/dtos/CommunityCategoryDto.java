package com.example.Skool.communityCategories.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommunityCategoryDto {
    @NotNull(message = "Category is required")
    @Length(min = 4, max = 20, message = "Category length should be between 4~20 characters")
    private String name;
}
