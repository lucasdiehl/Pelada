package com.pelada.api.mapper;

import com.pelada.api.dto.response.MatchDetailsResponse;
import com.pelada.api.dto.response.MatchFeedResponse;
import com.pelada.api.dto.response.MatchResponse;
import com.pelada.api.dto.response.OrganizerMatchResponse;
import com.pelada.api.entity.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {
    public MatchResponse toResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getOrganizer().getId(),
                match.getOrganizer().getFullName(),
                match.getTitle(),
                match.getMatchDate(),
                match.getMatchTime(),
                match.getFieldName(),
                match.getAddress(),
                match.getCity(),
                match.getPrice(),
                match.getMatchType(),
                match.getFieldType(),
                match.getSkillLevel(),
                match.getDescription(),
                match.getStatus(),
                match.getCreatedAt()
        );
    }

    public MatchFeedResponse toFeedResponse(Match match) {
        return new MatchFeedResponse(
                match.getId(),
                match.getOrganizer().getFullName(),
                match.getCity(),
                match.getMatchDate(),
                match.getMatchTime(),
                match.getMatchType(),
                match.getSkillLevel(),
                match.getPrice()
        );
    }

    public MatchDetailsResponse toDetailsResponse(Match match) {
        return new MatchDetailsResponse(
                match.getId(),
                match.getOrganizer().getUser().getId(),
                match.getOrganizer().getFullName(),
                match.getOrganizer().getPhotoUrl(),
                match.getTitle(),
                match.getMatchDate(),
                match.getMatchTime(),
                match.getFieldName(),
                match.getAddress(),
                match.getCity(),
                match.getPrice(),
                match.getMatchType(),
                match.getFieldType(),
                match.getSkillLevel(),
                match.getDescription(),
                match.getStatus(),
                match.getCreatedAt()
        );
    }

    public OrganizerMatchResponse toOrganizerResponse(Match match, Long applicationsCount, Boolean hasConfirmedGoalkeeper) {
        return new OrganizerMatchResponse(
                match.getId(),
                match.getTitle(),
                match.getMatchDate(),
                match.getMatchTime(),
                match.getCity(),
                match.getStatus(),
                applicationsCount,
                hasConfirmedGoalkeeper
        );
    }
}