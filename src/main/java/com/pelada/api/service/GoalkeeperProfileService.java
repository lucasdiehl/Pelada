package com.pelada.api.service;

import com.pelada.api.dto.request.UpdateGoalkeeperProfileRequest;
import com.pelada.api.dto.response.GoalkeeperProfileResponse;
import com.pelada.api.dto.response.GoalkeeperPublicProfileResponse;
import com.pelada.api.dto.response.ReviewResponse;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoalkeeperProfileService {
    GoalkeeperProfileResponse getMyProfile(User user);
    GoalkeeperProfileResponse updateMyProfile(User user, UpdateGoalkeeperProfileRequest request);
    GoalkeeperProfile getGoalkeeperProfile(User user);
    GoalkeeperPublicProfileResponse getPublicProfile(Long userId);
    Page<ReviewResponse> getReviews(Long userId, Pageable pageable);
}
