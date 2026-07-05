package com.pelada.api.controller;

import com.pelada.api.dto.request.UpdateMatchPreferencesRequest;
import com.pelada.api.dto.response.MatchPreferencesResponse;
import com.pelada.api.entity.User;
import com.pelada.api.service.MatchPreferenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goalkeepers/me/match-preferences")
@Tag(name = "Match Preferences", description = "Goalkeeper match preferences")
@RequiredArgsConstructor
public class MatchPreferenceController {
    private final MatchPreferenceService matchPreferenceService;

    @PreAuthorize("hasRole('GOALKEEPER')")
    @GetMapping
    public MatchPreferencesResponse getMatchPreferences(@AuthenticationPrincipal User user) {
        return matchPreferenceService.getMatchPreferences(user);
    }

    @PreAuthorize("hasRole('GOALKEEPER')")
    @PutMapping
    public MatchPreferencesResponse updateMatchPreferences(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateMatchPreferencesRequest request) {
        return matchPreferenceService.updateMatchPreferences(user, request);
    }
}
