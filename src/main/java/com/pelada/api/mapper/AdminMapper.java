package com.pelada.api.mapper;

import com.pelada.api.dto.response.AdminMatchResponse;
import com.pelada.api.dto.response.AdminUserResponse;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import com.pelada.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {
    private final ProfileService profileService;

    public AdminUserResponse toResponse(User user) {
        Profile profile = profileService.getProfileById(user.getId());
        return new AdminUserResponse(
                user.getId(),
                profile.getFullName(),
                user.getEmail(),
                user.getRole(),
                profile.getCity(),
                profile.getState(),
                user.getCreatedAt()
        );
    }

    public AdminMatchResponse toResponse(Match match) {
        return new AdminMatchResponse(
                match.getId(),
                match.getTitle(),
                match.getOrganizer().getFullName(),
                match.getCity(),
                match.getMatchDate(),
                match.getMatchTime(),
                match.getStatus(),
                match.getPrice(),
                match.getCreatedAt()
        );
    }
}
