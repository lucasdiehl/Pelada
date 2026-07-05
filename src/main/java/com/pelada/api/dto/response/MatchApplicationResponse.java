package com.pelada.api.dto.response;

import com.pelada.api.enums.ApplicationStatus;

import java.time.LocalDateTime;

public record MatchApplicationResponse(
        Long applicationId,
        Long goalkeeperUserId,
        String fullName,
        String photoUrl,
        String city,
        Integer age,
        Double averageRating,
        Long totalMatches,
        ApplicationStatus status,
        LocalDateTime appliedAt
) { }