package com.example.Skool.communities.dtos;

import com.example.Skool.common.validators.ListOfUrlsConstraint;
import com.example.Skool.common.validators.ValueOfEnumConstraint;
import com.example.Skool.communities.CommunityVisibility;
import com.example.Skool.communityQuestions.dtos.CommunityQuestionDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateCommunityDto {

    @NotNull(message = "Title is required")
    @Length(min = 4, max = 250, message = "Title should be between 4~250 characters")
    private String title;

    @NotNull(message = "Image is required")
    @URL
    private String imgUrl;

    private String description;

    @ValueOfEnumConstraint(enumClass = CommunityVisibility.class)
    private String visibility = CommunityVisibility.PUBLIC.toString();

    private List<CommunityQuestionDto> questions = new ArrayList<>();

    @PositiveOrZero
    @JsonAlias("cost_per_month")
    private int costPerMonth;

    @Positive
    @JsonAlias("category_id")
    private int categoryId;

    @ListOfUrlsConstraint
    private List<String> social = new ArrayList<>();
}
