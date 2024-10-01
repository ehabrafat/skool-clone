package com.example.Skool.communities.dtos;

import com.example.Skool.common.validators.ListOfUrlsConstraint;
import com.example.Skool.common.validators.ValueOfEnumConstraint;
import com.example.Skool.communities.CommunityVisibility;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateCommunityDto {

    @Positive
    private int id;

    @NotNull(message = "Community title is required")
    @Length(min = 4, max = 250, message = "Category length should be between 4~250 characters")
    private String title;

    private String description;

    @NotNull(message = "Community visibility is required")
    @ValueOfEnumConstraint(enumClass = CommunityVisibility.class)
    private String visibility;

    @PositiveOrZero
    @JsonAlias("cost_per_month")
    private int costPerMonth;

    @Positive
    @JsonAlias("category_id")
    private int categoryId;

    @ListOfUrlsConstraint
    private List<String> social = new ArrayList<>();
}
