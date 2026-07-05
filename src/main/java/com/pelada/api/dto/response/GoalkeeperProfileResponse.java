package com.pelada.api.dto.response;

import com.pelada.api.enums.ExperienceLevel;
import com.pelada.api.enums.PreferredFoot;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalkeeperProfileResponse(
        Long id,
        LocalDate birthDate,
        Integer age,
        BigDecimal height,
        PreferredFoot preferredFoot,
        ExperienceLevel experience,
        String bio,
        Boolean canTravel,
        Integer maxDistanceKm,
        BigDecimal expectedPrice
) { }