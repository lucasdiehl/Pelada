package com.pelada.api.mapper;

import com.pelada.api.dto.response.MatchPreferencesResponse;
import com.pelada.api.enums.MatchType;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MatchPreferenceMapper {
    public MatchPreferencesResponse toResponse(Set<MatchType> matchTypes) {
        return new MatchPreferencesResponse(matchTypes);
    }
}
