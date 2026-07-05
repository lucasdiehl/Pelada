package com.pelada.api.dto.response;

import com.pelada.api.enums.MatchStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrganizerMatchResponse(
        Long id,
        String title,
        LocalDate matchDate,
        LocalTime matchTime,
        String city,
        MatchStatus status,
        Long applicationsCount,
        Boolean hasConfirmedGoalkeeper
) { }