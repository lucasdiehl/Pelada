package com.pelada.api.dto.response;

import com.pelada.api.enums.MatchType;
import com.pelada.api.enums.SkillLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record MatchFeedResponse(
        Long id,
        String organizerName,
        String city,
        LocalDate matchDate,
        LocalTime matchTime,
        MatchType matchType,
        SkillLevel skillLevel,
        BigDecimal price
) { }