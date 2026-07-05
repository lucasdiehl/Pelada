package com.pelada.api.service;

import com.pelada.api.dto.request.UpdateMatchPreferencesRequest;
import com.pelada.api.dto.response.MatchPreferencesResponse;
import com.pelada.api.entity.User;
import jakarta.validation.Valid;

public interface MatchPreferenceService {
    MatchPreferencesResponse getMatchPreferences(User user);
    MatchPreferencesResponse updateMatchPreferences(User user, @Valid UpdateMatchPreferencesRequest request);
}
