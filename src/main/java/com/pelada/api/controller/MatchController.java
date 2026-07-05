package com.pelada.api.controller;

import com.pelada.api.dto.request.CreateMatchRequest;
import com.pelada.api.dto.filter.MatchFilter;
import com.pelada.api.dto.request.CreateReviewRequest;
import com.pelada.api.dto.request.UpdateMatchRequest;
import com.pelada.api.dto.response.*;
import com.pelada.api.entity.User;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.service.MatchService;
import com.pelada.api.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/matches")
@Tag(name = "Matches", description = "Manage football matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final ReviewService reviewService;

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping
    public MatchResponse createMatch(@AuthenticationPrincipal User user, @RequestBody @Valid CreateMatchRequest request) {
        return matchService.createMatch(user, request);
    }

    @GetMapping
    public Slice<MatchFeedResponse> findAll(MatchFilter filter, @PageableDefault(size = 20, sort = {"matchDate", "matchTime"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return matchService.findAll(filter, pageable);
    }

    @GetMapping("/{matchId}")
    public MatchDetailsResponse getMatchById(@PathVariable Long matchId) {
        return matchService.getMatchById(matchId);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PutMapping("/{matchId}/edit")
    public MatchResponse updateMatch(@PathVariable Long matchId, @AuthenticationPrincipal User user, @RequestBody @Valid UpdateMatchRequest request) {
        return matchService.updateMatch(matchId, user, request);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PatchMapping("/{matchId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelMatch(@PathVariable Long matchId, @AuthenticationPrincipal User user) {
        matchService.changeMatchStatus(matchId, user, MatchStatus.CANCELLED);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PatchMapping("/{matchId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeMatch(@PathVariable Long matchId, @AuthenticationPrincipal User user) {
        matchService.changeMatchStatus(matchId, user, MatchStatus.CLOSED);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping("/me")
    public Slice<OrganizerMatchResponse> getMyMatches(@AuthenticationPrincipal User user, @PageableDefault(size = 10, sort = "matchDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return matchService.getMyMatches(user, pageable);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/matches/{matchId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@PathVariable Long matchId, @AuthenticationPrincipal User user, @RequestBody @Valid CreateReviewRequest request) {
        reviewService.createReview(matchId, user, request);
    }
}