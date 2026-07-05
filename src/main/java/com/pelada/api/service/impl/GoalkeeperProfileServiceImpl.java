package com.pelada.api.service.impl;

import com.pelada.api.dto.request.UpdateGoalkeeperProfileRequest;
import com.pelada.api.dto.response.GoalkeeperProfileResponse;
import com.pelada.api.dto.response.GoalkeeperPublicProfileResponse;
import com.pelada.api.dto.response.ReviewResponse;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import com.pelada.api.enums.Role;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.exception.ResourceNotFoundException;
import com.pelada.api.mapper.GoalkeeperProfileMapper;
import com.pelada.api.repository.GoalkeeperRepository;
import com.pelada.api.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GoalkeeperProfileServiceImpl implements GoalkeeperProfileService {
    private final GoalkeeperRepository goalkeeperProfileRepository;
    private final ProfileService profileService;
    private final GoalkeeperProfileMapper goalkeeperProfileMapper;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ApplicationService applicationService;

    public GoalkeeperProfileResponse getMyProfile(User user) {
        return goalkeeperProfileMapper.toResponse(this.getGoalkeeperProfile(user));
    }

    @Override
    public GoalkeeperProfile getGoalkeeperProfile(User user) {
        validateGoalkeeper(user);
        Profile profile = profileService.getCurrentProfile(user);
        return goalkeeperProfileRepository.findByProfile(profile).orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }

    public GoalkeeperProfileResponse updateMyProfile(User user, UpdateGoalkeeperProfileRequest request) {
        validateGoalkeeper(user);
        Profile profile = profileService.getCurrentProfile(user);
        GoalkeeperProfile goalkeeperProfile = goalkeeperProfileRepository.findByProfile(profile)
                .orElseGet(() -> GoalkeeperProfile.builder()
                        .profile(profile)
                        .build());

        if (request.birthDate() != null) {
            goalkeeperProfile.setBirthDate(request.birthDate());
        }

        if (request.height() != null) {
            goalkeeperProfile.setHeightCm(request.height().shortValueExact());
        }
        if (request.preferredFoot() != null) {
            goalkeeperProfile.setPreferredFoot(request.preferredFoot());
        }
        if (request.experienceLevel() != null) {
            goalkeeperProfile.setExperienceLevel(request.experienceLevel());
        }
        if (StringUtils.isNotBlank(request.bio())) {
            goalkeeperProfile.setBio(request.bio());
        }
        if (request.canTravel() != null) {
            goalkeeperProfile.setCanTravel(request.canTravel());
        }
        if (request.maxDistanceKm() != null && request.maxDistanceKm() > 0) {
            goalkeeperProfile.setMaxDistanceKm(request.maxDistanceKm());
        }
        if (request.expectedPrice() != null) {
            goalkeeperProfile.setExpectedPrice(request.expectedPrice());
        }

        return goalkeeperProfileMapper.toResponse(goalkeeperProfileRepository.save(goalkeeperProfile));
    }

    @Override
    public GoalkeeperPublicProfileResponse getPublicProfile(Long userId) {
        GoalkeeperProfile goalkeeperProfile = this.getGoalkeeperProfile(userService.findUserById(userId));
        Profile profile = profileService.getProfileById(userId);
        validateGoalkeeper(profile.getUser());

        Double averageRating = reviewService.getAverageRating(profile);
        Long totalReviews = reviewService.getTotalReviews(profile);
        Long totalMatches = applicationService.getTotalMatches(profile);

        return goalkeeperProfileMapper.populatePublicGoalkeeperProfile(profile, goalkeeperProfile, averageRating, totalMatches, totalReviews);
    }

    @Override
    public Page<ReviewResponse> getReviews(Long userId, Pageable pageable) {
        Profile profile = profileService.getProfileById(userId);
        validateGoalkeeper(profile.getUser());
        return reviewService.getReviews(profile, pageable);
    }

    private void validateGoalkeeper(User user) {
        if (user.getRole() != Role.GOALKEEPER) {
            throw new BusinessException("Only goalkeepers can access this resource.");
        }
    }

}
