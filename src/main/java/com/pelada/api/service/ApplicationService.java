package com.pelada.api.service;

import com.pelada.api.dto.response.ApplicationResponse;
import com.pelada.api.dto.response.GoalkeeperApplicationResponse;
import com.pelada.api.dto.response.MatchApplicationResponse;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ApplicationService {
    Long getTotalMatches(Profile profile);
    ApplicationResponse apply(User user, Long matchId);
    Slice<MatchApplicationResponse> getApplications(Long matchId, User user, Pageable pageable);
    void acceptApplication(Long applicationId, User user);
    void rejectApplication(Long applicationId, User user);
    Slice<GoalkeeperApplicationResponse> getMyApplications(User user, Pageable pageable);
}
