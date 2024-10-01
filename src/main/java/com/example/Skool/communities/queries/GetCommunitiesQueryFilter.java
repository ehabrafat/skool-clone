package com.example.Skool.communities.queries;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class GetCommunitiesQueryFilter{
    @PositiveOrZero
    private int pageNum = 0;
    @PositiveOrZero
    private int pageSize = 10;
    private String type;
    private String price;
    @PositiveOrZero
    private int categoryId = 0;
}