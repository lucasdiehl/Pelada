package com.pelada.api.mapper;

import com.pelada.api.dto.response.ApplicationResponse;
import com.pelada.api.dto.response.GoalkeeperApplicationResponse;
import com.pelada.api.dto.response.MatchApplicationResponse;
import com.pelada.api.entity.Application;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.Profile;
import com.pelada.api.repository.ApplicationRepository;
import com.pelada.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
    private final ReviewService reviewService;
    private final ApplicationRepository applicationRepository;

    public ApplicationResponse toResponse(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getMatch().getId(),
                application.getGoalkeeper().getId(),
                application.getStatus(),
                application.getCreatedAt()
        );
    }

    public MatchApplicationResponse toMatchApplicationResponse(Application application) {
        Profile profile = application.getGoalkeeper().getProfile();
        Double averageRating = reviewService.getAverageRating(profile);
        Long totalMatches = applicationRepository.countAcceptedMatches(profile);

        return new MatchApplicationResponse(
                application.getId(),
                profile.getUser().getId(),
                profile.getFullName(),
                profile.getPhotoUrl(),
                profile.getCity(),
                calculateAge(application.getGoalkeeper().getBirthDate()),
                averageRating != null ? averageRating : 0.0,
                totalMatches,
                application.getStatus(),
                application.getCreatedAt()
        );
    }

    private Integer calculateAge(LocalDate birthDate) {
        return birthDate != null ? Period.between(birthDate, LocalDate.now()).getYears() : null;
    }

    public GoalkeeperApplicationResponse toGoalkeeperResponse(Application application) {
        Match match = application.getMatch();

        return new GoalkeeperApplicationResponse(
                application.getId(),
                match.getId(),
                match.getTitle(),
                match.getMatchDate(),
                match.getMatchTime(),
                match.getCity(),
                match.getPrice(),
                match.getMatchType(),
                application.getStatus(),
                match.getStatus(),
                application.getCreatedAt()
        );
    }
}