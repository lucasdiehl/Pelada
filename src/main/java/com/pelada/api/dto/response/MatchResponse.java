package com.pelada.api.dto.response;

import com.pelada.api.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record MatchResponse(
        Long id,
        Long organizerProfileId,
        String organizerName,
        String title,
        LocalDate matchDate,
        LocalTime matchTime,
        String fieldName,
        String address,
        String city,
        BigDecimal price,
        MatchType matchType,
        FieldType fieldType,
        SkillLevel skillLevel,
        String description,
        MatchStatus status,
        LocalDateTime createdAt
) { }