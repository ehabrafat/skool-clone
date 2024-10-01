package com.example.Skool.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class Handler {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    @ExceptionHandler(SkoolException.class)
    public ResponseEntity<?> handleSkoolException(SkoolException e, WebRequest request) {
        return createResponseEntity(e, request);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleSQLException(SQLException e) {
        String errorCode = e.getSQLState();
        if(errorCode.equals("23505")){
            String message = e.getMessage();
            String detail = message.substring(4 + message.indexOf("Key"));
            String key = detail.split(" ")[0].split("=")[0].replaceAll("[()]", "");
            String responseMessage = key + " already exists";
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> mp = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            mp.put(fieldName, message);
        });
        return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest webRequest) {
        ExceptionModel errorDetails = new ExceptionModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                this.getClass().getName(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now()),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<?> createResponseEntity(SkoolException e, WebRequest request) {
        ExceptionModel errorDetails = ExceptionModel.builder()
                .message(e.getMessage())
                .statusCode(e.getStatusCode().value())
                .location(e.getLocation())
                .timeStamp(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now()))
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, e.getStatusCode());
    }

}
