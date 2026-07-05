package com.pelada.api.mapper;

import com.pelada.api.dto.response.GoalkeeperProfileResponse;
import com.pelada.api.dto.response.GoalkeeperPublicProfileResponse;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
public class GoalkeeperProfileMapper {
    public GoalkeeperProfileResponse toResponse(GoalkeeperProfile goalkeeperProfile) {
        if (goalkeeperProfile == null) {
            return null;
        }

        return new GoalkeeperProfileResponse(
                goalkeeperProfile.getId(),
                goalkeeperProfile.getBirthDate(),
                calculateAge(goalkeeperProfile.getBirthDate()),
                BigDecimal.valueOf(goalkeeperProfile.getHeightCm()),
                goalkeeperProfile.getPreferredFoot(),
                goalkeeperProfile.getExperienceLevel(),
                goalkeeperProfile.getBio(),
                goalkeeperProfile.getCanTravel(),
                goalkeeperProfile.getMaxDistanceKm(),
                goalkeeperProfile.getExpectedPrice());
    }

    private Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return null;
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public GoalkeeperPublicProfileResponse populatePublicGoalkeeperProfile(Profile profile, GoalkeeperProfile goalkeeperProfile, Double averageRating, Long totalMatches, Long totalReviews) {
        return new GoalkeeperPublicProfileResponse(
                profile.getUser().getId(),
                profile.getFullName(),
                profile.getPhotoUrl(),
                profile.getCity(),
                profile.getState(),
                calculateAge(goalkeeperProfile.getBirthDate()),
                BigDecimal.valueOf(goalkeeperProfile.getHeightCm()),
                goalkeeperProfile.getPreferredFoot(),
                goalkeeperProfile.getExperienceLevel(),
                goalkeeperProfile.getBio(),
                goalkeeperProfile.getCanTravel(),
                goalkeeperProfile.getMaxDistanceKm(),
                goalkeeperProfile.getExpectedPrice(),
                averageRating != null ? averageRating : 0.0,
                totalMatches,
                totalReviews
        );
    }
}
