package com.pelada.api.service;

import com.pelada.api.dto.filter.MatchAdminFilter;
import com.pelada.api.dto.filter.UserFilter;
import com.pelada.api.dto.response.AdminMatchResponse;
import com.pelada.api.dto.response.AdminUserResponse;
import com.pelada.api.enums.MatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    Page<AdminUserResponse> findAll(UserFilter filter, Pageable pageable);
    Page<AdminMatchResponse> findAllMatches(MatchAdminFilter filter, Pageable pageable);
    void changeStatusMatch(Long matchId, MatchStatus status);
    void deleteReview(Long reviewId);
    void blockUser(Long userId);
    void unblockUser(Long userId);
}
