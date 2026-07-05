package com.pelada.api.service.impl;

import com.pelada.api.dto.filter.MatchAdminFilter;
import com.pelada.api.dto.request.CreateMatchRequest;
import com.pelada.api.dto.filter.MatchFilter;
import com.pelada.api.dto.request.UpdateMatchRequest;
import com.pelada.api.dto.response.*;
import com.pelada.api.entity.Application;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import com.pelada.api.enums.ApplicationStatus;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.enums.Role;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.exception.ResourceNotFoundException;
import com.pelada.api.mapper.MatchMapper;
import com.pelada.api.repository.ApplicationRepository;
import com.pelada.api.repository.MatchRepository;
import com.pelada.api.repository.specification.MatchSpecification;
import com.pelada.api.service.MatchService;
import com.pelada.api.service.NotificationService;
import com.pelada.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final ProfileService profileService;
    private final MatchMapper matchMapper;
    private final ApplicationRepository applicationRepository;
    private final NotificationService notificationService;

    @Override
    public MatchResponse createMatch(User user, CreateMatchRequest request) {
        if (user.getRole() != Role.ORGANIZER) {
            throw new BusinessException("Only organizers can create matches.");
        }

        if (request.matchDate().equals(LocalDate.now()) && request.matchTime().isBefore(LocalTime.now())) {
            throw new BusinessException("The match time cannot be in the past.");
        }

        Profile organizer = profileService.getCurrentProfile(user);
        Match match = Match.builder()
                .organizer(organizer)
                .title(request.title())
                .matchDate(request.matchDate())
                .matchTime(request.matchTime())
                .fieldName(request.fieldName())
                .address(request.address())
                .city(request.city())
                .price(request.price())
                .matchType(request.matchType())
                .fieldType(request.fieldType())
                .skillLevel(request.skillLevel())
                .description(request.description())
                .status(MatchStatus.OPEN)
                .build();

        return matchMapper.toResponse(matchRepository.save(match));
    }

    @Override
    public Slice<MatchFeedResponse> findAll(MatchFilter filter, Pageable pageable) {
        return matchRepository.findAll(MatchSpecification.withFilters(filter), pageable).map(matchMapper::toFeedResponse);
    }

    @Override
    public MatchDetailsResponse getMatchById(Long matchId) {
        Match match = this.getMatch(matchId);
        return matchMapper.toDetailsResponse(match);
    }

    @Override
    public MatchResponse updateMatch(Long matchId, User user, UpdateMatchRequest request) {
        Match match = this.getOwnedMatch(matchId, user);

        if (StringUtils.isNotBlank(request.title())) {
            match.setTitle(request.title());
        }
        if (request.matchDate() != null) {
            match.setMatchDate(request.matchDate());
        }
        if (request.matchTime() != null) {
            match.setMatchTime(request.matchTime());
        }
        if (StringUtils.isNotBlank(request.fieldName())) {
            match.setFieldName(request.fieldName());
        }
        if (StringUtils.isNotBlank(request.address())) {
            match.setAddress(request.address());
        }
        if (StringUtils.isNotBlank(request.city())) {
            match.setCity(request.city());
        }
        if (request.price() != null) {
            match.setPrice(request.price());
        }
        if (request.matchType() != null) {
            match.setMatchType(request.matchType());
        }
        if (request.fieldType() != null) {
            match.setFieldType(request.fieldType());
        }
        if (request.skillLevel() != null) {
            match.setSkillLevel(request.skillLevel());
        }
        if (StringUtils.isNotBlank(request.description())) {
            match.setDescription(request.description());
        }

        return matchMapper.toResponse(matchRepository.save(match));
    }

    @Override
    public void changeMatchStatus(Long matchId, User user, MatchStatus status) {
        Match match = this.getOwnedMatch(matchId, user);
        if (match.getStatus() == status) {
            return;
        }

        match.setStatus(status);
        matchRepository.save(match);
        if (status == MatchStatus.CANCELLED || status == MatchStatus.CLOSED) {
            notifyMatchCancelled(match);
        }
    }

    private void notifyMatchCancelled(Match match) {
        List<Application> applications = applicationRepository.findAllByMatch(match);
        applications.stream()
                .filter(application -> application.getStatus() != ApplicationStatus.REJECTED)
                .forEach(notificationService::notifyMatchCancelled);
    }

    @Override
    public Slice<OrganizerMatchResponse> getMyMatches(User user, Pageable pageable) {
        if (user.getRole() != Role.ORGANIZER) {
            throw new BusinessException("Only organizers can access this resource.");
        }

        Profile organizer = profileService.getCurrentProfile(user);
        return matchRepository.findByOrganizer(organizer, pageable)
                .map(match -> {
                    long applicationsCount = applicationRepository.countByMatch(match);
                    boolean hasConfirmedGoalkeeper = matchAlreadyAccepted(match);
                    return matchMapper.toOrganizerResponse(match, applicationsCount, hasConfirmedGoalkeeper);
                });
    }

    @Override
    public Match getMatch(Long matchId) {
        return matchRepository.findById(matchId).orElseThrow(() -> new ResourceNotFoundException("Match not found."));
    }

    @Override
    public Match getOwnedMatch(Long matchId, User user) {
        Match match = this.getMatch(matchId);
        if (!match.getOrganizer().getUser().getId().equals(user.getId())) {
            throw new BusinessException("You are not allowed to edit this match.");
        }

        if (match.getStatus() != MatchStatus.OPEN) {
            throw new BusinessException("Only open matches can be edited.");
        }

        if (matchAlreadyAccepted(match)) {
            throw new BusinessException("This match already has a confirmed goalkeeper.");
        }
        return match;
    }

    private boolean matchAlreadyAccepted(Match match) {
        return applicationRepository.existsByMatchAndStatus(match, ApplicationStatus.ACCEPTED);
    }

    @Override
    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    @Override
    public Page<Match> findAll(MatchAdminFilter filter, Pageable pageable) {
        return matchRepository.findAll(MatchSpecification.withAdminFilters(filter), pageable);
    }
}
