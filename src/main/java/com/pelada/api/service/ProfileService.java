package com.pelada.api.service;

import com.pelada.api.dto.request.CompleteProfileRequest;
import com.pelada.api.dto.request.UpdateProfileRequest;
import com.pelada.api.dto.response.ProfileResponse;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileResponse completeProfile(User user, @Valid CompleteProfileRequest request);
    ProfileResponse getMyProfile(User user);
    ProfileResponse updateMyProfile(User user, UpdateProfileRequest request);
    ProfileResponse getProfileByUserId(Long userId);
    void deleteMyProfile(User user);
    Profile getCurrentProfile(User user);
    Profile getProfileById(Long userId);
    ProfileResponse uploadPhoto(User user, MultipartFile file);
}
