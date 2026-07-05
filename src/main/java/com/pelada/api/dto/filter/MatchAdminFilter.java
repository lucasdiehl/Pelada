package com.pelada.api.dto.filter;

import com.pelada.api.enums.FieldType;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.enums.MatchType;
import com.pelada.api.enums.SkillLevel;

import java.time.LocalDate;

public record MatchAdminFilter(
        String search,
        MatchStatus status,
        String city,
        MatchType matchType,
        FieldType fieldType,
        SkillLevel skillLevel,
        LocalDate date
) { }