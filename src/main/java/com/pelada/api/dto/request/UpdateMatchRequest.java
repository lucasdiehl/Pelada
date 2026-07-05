package com.pelada.api.dto.request;

import com.pelada.api.enums.FieldType;
import com.pelada.api.enums.MatchType;
import com.pelada.api.enums.SkillLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateMatchRequest(

        @Size(max = 150)
        String title,

        @FutureOrPresent
        LocalDate matchDate,

        LocalTime matchTime,

        String fieldName,

        String address,

        String city,

        BigDecimal price,

        MatchType matchType,

        FieldType fieldType,

        SkillLevel skillLevel,

        @Size(max = 500)
        String description

) { }