package com.pelada.api.service.impl;

import com.pelada.api.dto.filter.MatchAdminFilter;
import com.pelada.api.dto.filter.UserFilter;
import com.pelada.api.dto.response.AdminMatchResponse;
import com.pelada.api.dto.response.AdminUserResponse;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.User;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.enums.UserStatus;
import com.pelada.api.mapper.AdminMapper;
import com.pelada.api.service.AdminService;
import com.pelada.api.service.MatchService;
import com.pelada.api.service.ReviewService;
import com.pelada.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final AdminMapper adminMapper;
    private final MatchService matchService;
    private final ReviewService reviewService;

    @Override
    public Page<AdminUserResponse> findAll(UserFilter filter, Pageable pageable) {
        return userService.findAll(filter, pageable).map(adminMapper::toResponse);
    }

    @Override
    public Page<AdminMatchResponse> findAllMatches(MatchAdminFilter filter, Pageable pageable) {
        return matchService.findAll(filter, pageable).map(adminMapper::toResponse);
    }

    @Transactional
    @Override
    public void changeStatusMatch(Long matchId, MatchStatus status) {
        Match match = matchService.getMatch(matchId);
        match.setStatus(status);
        matchService.saveMatch(match);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        User user = userService.findUserById(userId);
        if (user.getStatus() == UserStatus.BLOCKED) {
            return;
        }
        user.setStatus(UserStatus.BLOCKED);
        userService.save(user);
    }

    @Override
    @Transactional
    public void unblockUser(Long userId) {
        User user = userService.findUserById(userId);
        if (user.getStatus() == UserStatus.ACTIVE) {
            return;
        }
        user.setStatus(UserStatus.ACTIVE);
        userService.save(user);
    }
}
