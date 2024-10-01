package com.example.Skool.common.exceptions;

import lombok.*;

@Data
@Builder
public class ExceptionModel {

    private int statusCode;

    private String message;

    private String location;

    private String timeStamp;

    private String description;
}
