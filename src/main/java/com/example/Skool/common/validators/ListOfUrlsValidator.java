package com.example.Skool.common.validators;

import com.example.Skool.communities.CommunityService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ListOfUrlsValidator implements ConstraintValidator<ListOfUrlsConstraint, List<String>> {

    @Override
    public void initialize(ListOfUrlsConstraint annotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        return value.stream().allMatch(input -> {
            if(input == null || input.isEmpty()) return false;
            try {
                URI uri = URI.create(input);
                return uri.getScheme() != null && uri.getHost() != null;
            } catch (IllegalArgumentException e) {
                return false; // If exception is thrown, URL is invalid
            }
        });
    }
}