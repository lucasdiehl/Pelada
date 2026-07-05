package com.pelada.api.dto.response;

import com.pelada.api.enums.ExperienceLevel;
import com.pelada.api.enums.PreferredFoot;

import java.math.BigDecimal;

public record GoalkeeperPublicProfileResponse(
        Long userId,
        String fullName,
        String photoUrl,
        String city,
        String state,
        Integer age,
        BigDecimal height,
        PreferredFoot preferredFoot,
        ExperienceLevel experienceLevel,
        String bio,
        Boolean canTravel,
        Integer maxDistanceKm,
        BigDecimal expectedPrice,
        Double averageRating,
        Long totalMatches,
        Long totalReviews
) { }
