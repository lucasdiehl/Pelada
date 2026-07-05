package com.pelada.api.controller;

import com.pelada.api.dto.filter.MatchAdminFilter;
import com.pelada.api.dto.filter.UserFilter;
import com.pelada.api.dto.response.AdminMatchResponse;
import com.pelada.api.dto.response.AdminUserResponse;
import com.pelada.api.entity.User;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Admin management")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public Page<AdminUserResponse> findAll(UserFilter filter, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return adminService.findAll(filter, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/matches")
    public Page<AdminMatchResponse> findAllMatches(MatchAdminFilter filter, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return adminService.findAllMatches(filter, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/matches/{matchId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelMatch(@PathVariable Long matchId, @AuthenticationPrincipal User user) {
        adminService.changeStatusMatch(matchId, MatchStatus.CANCELLED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/matches/{matchId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeMatch(@PathVariable Long matchId, @AuthenticationPrincipal User user) {
        adminService.changeStatusMatch(matchId, MatchStatus.CLOSED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/reviews/{reviewId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReview(@PathVariable Long reviewId) {
        adminService.deleteReview(reviewId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{userId}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void blockUser(@PathVariable Long userId) {
        adminService.blockUser(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{userId}/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblockUser(@PathVariable Long userId) {
        adminService.unblockUser(userId);
    }
}
