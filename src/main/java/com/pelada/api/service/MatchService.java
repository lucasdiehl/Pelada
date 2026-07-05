package com.pelada.api.service;

import com.pelada.api.dto.filter.MatchAdminFilter;
import com.pelada.api.dto.request.CreateMatchRequest;
import com.pelada.api.dto.filter.MatchFilter;
import com.pelada.api.dto.request.UpdateMatchRequest;
import com.pelada.api.dto.response.*;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.User;
import com.pelada.api.enums.MatchStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MatchService {
    MatchResponse createMatch(User user, CreateMatchRequest request);
    Slice<MatchFeedResponse> findAll(MatchFilter filter, Pageable pageable);
    MatchDetailsResponse getMatchById(Long matchId);
    MatchResponse updateMatch(Long matchId, User user, @Valid UpdateMatchRequest request);
    void changeMatchStatus(Long matchId, User user, MatchStatus status);
    Slice<OrganizerMatchResponse> getMyMatches(User user, Pageable pageable);
    Match getMatch(Long matchId);
    Match getOwnedMatch(Long matchId, User user);
    void saveMatch(Match match);
    Page<Match> findAll(MatchAdminFilter filter, Pageable pageable);
}
