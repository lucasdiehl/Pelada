package com.pelada.api.controller;

import com.pelada.api.dto.request.UpdateGoalkeeperProfileRequest;
import com.pelada.api.dto.response.GoalkeeperProfileResponse;
import com.pelada.api.dto.response.GoalkeeperPublicProfileResponse;
import com.pelada.api.dto.response.ReviewResponse;
import com.pelada.api.entity.User;
import com.pelada.api.service.GoalkeeperProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goalkeeper/profile")
@Tag(name = "Goalkeeper Profiles", description = "Goalkeeper specific profile")
@RequiredArgsConstructor
public class GoalkeeperProfileController {
    private final GoalkeeperProfileService goalkeeperProfileService;

    @PreAuthorize("hasRole('GOALKEEPER')")
    @GetMapping("/me")
    public GoalkeeperProfileResponse getMyProfile(@AuthenticationPrincipal User user) {
        return goalkeeperProfileService.getMyProfile(user);
    }

    @PreAuthorize("hasRole('GOALKEEPER')")
    @PutMapping("/me")
    public GoalkeeperProfileResponse updateMyProfile(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateGoalkeeperProfileRequest request) {
        return goalkeeperProfileService.updateMyProfile(user, request);
    }

    @GetMapping("/public/{userId}/valuation")
    public GoalkeeperPublicProfileResponse getPublicProfile(@PathVariable Long userId) {
        return goalkeeperProfileService.getPublicProfile(userId);
    }

    @GetMapping("/public/{userId}/reviews")
    public Page<ReviewResponse> getReviews(@PathVariable Long userId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return goalkeeperProfileService.getReviews(userId, pageable);
    }
}