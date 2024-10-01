package com.example.Skool.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PageResponseDto <T>  {
    private List<T> content;
    private int numberOfElements;
    private long totalNumberOfElements;
    private boolean hasNext;
    private boolean hasPrevious;
}
