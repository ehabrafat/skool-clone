package com.example.Skool.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SkoolException extends RuntimeException {

    private final String location;
    private final HttpStatus statusCode;

    public SkoolException(String message, String location, HttpStatus statusCode) {
        super(message);
        this.location = location;
        this.statusCode = statusCode;
    }
}
