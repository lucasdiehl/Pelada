package com.pelada.api.dto.request;

import com.pelada.api.enums.ExperienceLevel;
import com.pelada.api.enums.PreferredFoot;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateGoalkeeperProfileRequest(

        LocalDate birthDate,

        BigDecimal height,

        PreferredFoot preferredFoot,

        ExperienceLevel experienceLevel,

        @Size(max = 1000)
        String bio,

        Boolean canTravel,

        Integer maxDistanceKm,

        BigDecimal expectedPrice

) { }