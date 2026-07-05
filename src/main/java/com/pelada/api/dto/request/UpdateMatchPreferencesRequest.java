package com.pelada.api.dto.request;

import com.pelada.api.enums.MatchType;

import java.util.Set;

public record UpdateMatchPreferencesRequest(

        Set<MatchType> matchTypes

) { }