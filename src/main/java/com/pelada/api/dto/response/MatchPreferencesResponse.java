package com.pelada.api.dto.response;

import com.pelada.api.enums.MatchType;

import java.util.Set;

public record MatchPreferencesResponse(
        Set<MatchType> matchTypes
) { }
