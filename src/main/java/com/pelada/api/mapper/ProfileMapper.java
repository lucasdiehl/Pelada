package com.pelada.api.mapper;

import com.pelada.api.dto.response.ProfileResponse;
import com.pelada.api.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileResponse toResponse(Profile profile) {
        if (profile == null) {
            return null;
        }

        return new ProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getFullName(),
                profile.getPhone(),
                profile.getCity(),
                profile.getState(),
                profile.getPhotoUrl(),
                profile.getUser().getRole());
    }

}