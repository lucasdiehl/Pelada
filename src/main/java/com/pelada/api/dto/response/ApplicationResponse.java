package com.pelada.api.dto.response;

import com.pelada.api.enums.ApplicationStatus;

import java.time.LocalDateTime;

public record ApplicationResponse(
        Long id,
        Long matchId,
        Long goalkeeperProfileId,
        ApplicationStatus status,
        LocalDateTime createdAt
) { }