package com.pelada.api.dto.request;

import com.pelada.api.enums.FieldType;
import com.pelada.api.enums.MatchType;
import com.pelada.api.enums.SkillLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateMatchRequest(

        @NotBlank(message = "Title is required.")
        @Size(max = 150, message = "Title must have at most 150 characters.")
        String title,

        @NotNull(message = "Match date is required.")
        @FutureOrPresent(message = "Match date cannot be in the past.")
        LocalDate matchDate,

        @NotNull(message = "Match time is required.")
        LocalTime matchTime,

        @NotBlank(message = "Field name is required.")
        @Size(max = 150)
        String fieldName,

        @NotBlank(message = "Address is required.")
        @Size(max = 255)
        String address,

        @NotBlank(message = "City is required.")
        @Size(max = 100)
        String city,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "1.00", inclusive = true)
        @Digits(integer = 6, fraction = 2)
        BigDecimal price,

        @NotNull(message = "Match type is required.")
        MatchType matchType,

        @NotNull(message = "Field type is required.")
        FieldType fieldType,

        @NotNull(message = "Skill level is required.")
        SkillLevel skillLevel,

        @Size(max = 500, message = "Description must have at most 500 characters.")
        String description

) { }