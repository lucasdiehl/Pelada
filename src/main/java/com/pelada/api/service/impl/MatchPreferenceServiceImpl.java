package com.pelada.api.service.impl;

import com.pelada.api.dto.request.UpdateMatchPreferencesRequest;
import com.pelada.api.dto.response.MatchPreferencesResponse;
import com.pelada.api.entity.GoalkeeperMatchPreference;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.User;
import com.pelada.api.enums.MatchType;
import com.pelada.api.mapper.MatchPreferenceMapper;
import com.pelada.api.repository.MatchPreferenceRepository;
import com.pelada.api.service.GoalkeeperProfileService;
import com.pelada.api.service.MatchPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchPreferenceServiceImpl implements MatchPreferenceService {
    private final MatchPreferenceRepository matchPreferenceRepository;
    private final GoalkeeperProfileService goalkeeperProfileService;
    private final MatchPreferenceMapper matchPreferenceMapper;

    @Override
    public MatchPreferencesResponse getMatchPreferences(User user) {
        GoalkeeperProfile goalkeeperProfile = goalkeeperProfileService.getGoalkeeperProfile(user);
        Set<MatchType> matchTypes = matchPreferenceRepository
                .findAllByGoalkeeperProfile(goalkeeperProfile)
                .stream()
                .map(GoalkeeperMatchPreference::getMatchType)
                .collect(Collectors.toSet());

        return matchPreferenceMapper.toResponse(matchTypes);
    }

    @Override
    public MatchPreferencesResponse updateMatchPreferences(User user, UpdateMatchPreferencesRequest request) {
        GoalkeeperProfile goalkeeperProfile = goalkeeperProfileService.getGoalkeeperProfile(user);
        matchPreferenceRepository.deleteAllByGoalkeeperProfile(goalkeeperProfile);
        List<GoalkeeperMatchPreference> matchPreferences = request.matchTypes()
                .stream()
                .map(matchType -> GoalkeeperMatchPreference.builder()
                        .goalkeeperProfile(goalkeeperProfile)
                        .matchType(matchType)
                        .build())
                .toList();
        matchPreferenceRepository.saveAll(matchPreferences);

        return matchPreferenceMapper.toResponse(request.matchTypes());
    }
}
