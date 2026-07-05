package com.pelada.api.dto.response;

import com.pelada.api.enums.FieldType;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.enums.MatchType;
import com.pelada.api.enums.SkillLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record MatchDetailsResponse(
        Long id,
        Long organizerId,
        String organizerName,
        String organizerPhoto,
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
