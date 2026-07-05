package com.pelada.api.controller;

import com.pelada.api.dto.response.ApplicationResponse;
import com.pelada.api.dto.response.GoalkeeperApplicationResponse;
import com.pelada.api.dto.response.MatchApplicationResponse;
import com.pelada.api.entity.User;
import com.pelada.api.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches/{matchId}/applications")
@Tag(name = "Applications", description = "Manage matches applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PreAuthorize("hasRole('GOALKEEPER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse apply(@AuthenticationPrincipal User user, @PathVariable Long matchId) {
        return applicationService.apply(user, matchId);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping
    public Slice<MatchApplicationResponse> getApplications(@PathVariable Long matchId, @AuthenticationPrincipal User user, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return applicationService.getApplications(matchId, user, pageable);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PatchMapping("/{applicationId}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptApplication(@PathVariable Long applicationId, @AuthenticationPrincipal User user) {
        applicationService.acceptApplication(applicationId, user);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PatchMapping("/{applicationId}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectApplication(@PathVariable Long applicationId, @AuthenticationPrincipal User user) {
        applicationService.rejectApplication(applicationId, user);
    }

    @PreAuthorize("hasRole('GOALKEEPER')")
    @GetMapping("/me")
    public Slice<GoalkeeperApplicationResponse> getMyApplications(@AuthenticationPrincipal User user, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return applicationService.getMyApplications(user, pageable);
    }
}