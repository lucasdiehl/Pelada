package com.pelada.api.dto.filter;

import com.pelada.api.enums.FieldType;
import com.pelada.api.enums.MatchType;
import com.pelada.api.enums.SkillLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MatchFilter(
        String search,
        String city,
        LocalDate date,
        MatchType matchType,
        FieldType fieldType,
        SkillLevel skillLevel,
        BigDecimal minPrice,
        BigDecimal maxPrice
) { }
