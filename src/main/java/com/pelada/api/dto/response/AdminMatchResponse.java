package com.pelada.api.dto.response;

import com.pelada.api.enums.MatchStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AdminMatchResponse(
        Long id,
        String title,
        String organizerName,
        String city,
        LocalDate matchDate,
        LocalTime matchTime,
        MatchStatus status,
        BigDecimal price,
        LocalDateTime createdAt
) { }
