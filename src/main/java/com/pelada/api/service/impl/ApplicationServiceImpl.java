package com.pelada.api.service.impl;

import com.pelada.api.dto.response.ApplicationResponse;
import com.pelada.api.dto.response.GoalkeeperApplicationResponse;
import com.pelada.api.dto.response.MatchApplicationResponse;
import com.pelada.api.entity.*;
import com.pelada.api.enums.ApplicationStatus;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.enums.Role;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.exception.ResourceNotFoundException;
import com.pelada.api.mapper.ApplicationMapper;
import com.pelada.api.repository.ApplicationRepository;
import com.pelada.api.repository.GoalkeeperRepository;
import com.pelada.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final MatchService matchService;
    private final GoalkeeperRepository goalkeeperRepository;
    private final ApplicationMapper applicationMapper;
    private final NotificationService notificationService;
    private final ProfileService profileService;

    @Override
    public Long getTotalMatches(Profile profile) {
        return applicationRepository.countAcceptedMatches(profile);
    }

    @Override
    public ApplicationResponse apply(User user, Long matchId) {
        GoalkeeperProfile goalkeeper = getGoalkeeperProfile(user);
        Match match = matchService.getMatch(matchId);
        validateApplication(match, user, goalkeeper);
        Application application = Application.builder()
                .match(match)
                .goalkeeper(goalkeeper)
                .status(ApplicationStatus.PENDING)
                .build();
        Application savedApplication = applicationRepository.save(application);
        notificationService.notifyNewApplication(match, goalkeeper);
        return applicationMapper.toResponse(savedApplication);
    }

    private GoalkeeperProfile getGoalkeeperProfile(User user) {
        validateGoalkeeper(user);
        Profile profile = profileService.getCurrentProfile(user);
        return goalkeeperRepository.findByProfile(profile).orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }

    private void validateGoalkeeper(User user) {
        if (user.getRole() != Role.GOALKEEPER) {
            throw new BusinessException("Only goalkeepers can access this resource.");
        }
    }

    @Override
    public Slice<MatchApplicationResponse> getApplications(Long matchId, User user, Pageable pageable) {
        Match match = matchService.getOwnedMatch(matchId, user);
        return applicationRepository.findByMatch(match, pageable).map(applicationMapper::toMatchApplicationResponse);
    }

    @Override
    @Transactional
    public void acceptApplication(Long applicationId, User user) {
        Application application = getApplication(applicationId);
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new BusinessException("Only pending applications can be accepted.");
        }

        Match match = application.getMatch();
        if (!match.getOrganizer().getUser().getId().equals(user.getId())) {
            throw new BusinessException("You are not allowed to manage this match.");
        }
        if (match.getStatus() != MatchStatus.CLOSED) {
            throw new BusinessException("Match must be closed before accepting a goalkeeper.");
        }

        List<Application> applications = applicationRepository.findAllByMatch(match);
        for (Application applied : applications) {
            if (applied.getId().equals(applicationId)) {
                applied.setStatus(ApplicationStatus.ACCEPTED);
            } else if (applied.getStatus() == ApplicationStatus.PENDING) {
                applied.setStatus(ApplicationStatus.REJECTED);
                notificationService.notifyApplicationRejected(applied);
            }
        }

        match.setStatus(MatchStatus.CONFIRMED);
        applicationRepository.saveAll(applications);
        matchService.saveMatch(match);

        Application acceptedApplication = applications.stream()
                .filter(a -> a.getStatus() == ApplicationStatus.ACCEPTED)
                .findFirst()
                .orElseThrow();
        notificationService.notifyApplicationAccepted(acceptedApplication);
    }

    @Override
    @Transactional
    public void rejectApplication(Long applicationId, User user) {
        Application application = getApplication(applicationId);
        Match match = application.getMatch();
        if (!match.getOrganizer().getUser().getId().equals(user.getId())) {
            throw new BusinessException("You are not allowed to manage this match.");
        }
        if (application.getStatus() == ApplicationStatus.REJECTED) {
            return;
        }
        if (application.getStatus() == ApplicationStatus.ACCEPTED) {
            throw new BusinessException("Accepted application cannot be rejected.");
        }
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new BusinessException("Only pending applications can be rejected.");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);
        notificationService.notifyApplicationRejected(application);
    }

    @Override
    public Slice<GoalkeeperApplicationResponse> getMyApplications(User user, Pageable pageable) {
        GoalkeeperProfile goalkeeper = getGoalkeeperProfile(user);
        return applicationRepository.findByGoalkeeper(goalkeeper, pageable).map(applicationMapper::toGoalkeeperResponse);
    }

    private Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application not found."));
    }

    private void validateApplication(Match match, User user, GoalkeeperProfile goalkeeper) {
        if (match.getStatus() != MatchStatus.OPEN) {
            throw new BusinessException("Applications are closed for this match.");
        }

        if (match.getOrganizer().getUser().getId().equals(user.getId())) {
            throw new BusinessException("You cannot apply to your own match.");
        }

        if (applicationRepository.existsByMatchAndGoalkeeper(match, goalkeeper)) {
            throw new BusinessException("You have already applied to this match.");
        }
    }
}
