package com.pelada.api.dto.response;

import com.pelada.api.enums.ApplicationStatus;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.enums.MatchType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record GoalkeeperApplicationResponse(
        Long applicationId,
        Long matchId,
        String matchTitle,
        LocalDate matchDate,
        LocalTime matchTime,
        String city,
        BigDecimal price,
        MatchType matchType,
        ApplicationStatus applicationStatus,
        MatchStatus matchStatus,
        LocalDateTime appliedAt
) { }
