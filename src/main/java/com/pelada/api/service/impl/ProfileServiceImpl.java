package com.pelada.api.service.impl;

import com.pelada.api.dto.request.CompleteProfileRequest;
import com.pelada.api.dto.request.UpdateProfileRequest;
import com.pelada.api.dto.response.ProfileResponse;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.exception.ResourceNotFoundException;
import com.pelada.api.mapper.ProfileMapper;
import com.pelada.api.repository.ProfileRepository;
import com.pelada.api.service.ProfileService;
import com.pelada.api.service.StorageService;
import com.pelada.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, "image/webp");

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final StorageService storageService;

    @Override
    public ProfileResponse completeProfile(User user, CompleteProfileRequest request) {
        return profileRepository.findByUser(user)
                .map(profileMapper::toResponse)
                .orElseGet(() -> {
                    Profile profile = Profile.builder()
                            .user(user)
                            .fullName(request.fullName())
                            .phone(request.phone())
                            .city(request.city())
                            .state(request.state())
                            .build();
                    return profileMapper.toResponse(profileRepository.save(profile));
                });
    }

    @Override
    public ProfileResponse getMyProfile(User user) {
        return profileRepository.findByUser(user)
                .map(profileMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }

    @Override
    public ProfileResponse updateMyProfile(User user, UpdateProfileRequest request) {
        Profile profile = getCurrentProfile(user);

        if (StringUtils.isNotBlank(request.fullName())) {
            profile.setFullName(request.fullName());
        }
        if (StringUtils.isNotBlank(request.phone())) {
            profile.setPhone(request.phone());
        }
        if (StringUtils.isNotBlank(request.city())) {
            profile.setCity(request.city());
        }
        if (StringUtils.isNotBlank(request.state())) {
            profile.setState(request.state());
        }
        if (StringUtils.isNotBlank(request.photoUrl())) {
            profile.setPhotoUrl(request.photoUrl());
        }

        return profileMapper.toResponse(profileRepository.save(profile));
    }

    @Override
    public ProfileResponse getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .map(profileMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }

    @Override
    public void deleteMyProfile(User user) {
        Profile profile = getCurrentProfile(user);
        userService.deleteProfile(profile.getUser());
    }

    @Override
    public Profile getCurrentProfile(User user) {
        return profileRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }

    @Override
    public Profile getProfileById(Long userId) {
        return profileRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }


    public ProfileResponse uploadPhoto(User user, MultipartFile file) {
        Profile profile = this.getCurrentProfile(user);
        validatePhoto(file);
        String photoUrl = storageService.upload(file);
        if (profile.getPhotoUrl() != null) {
            storageService.delete(profile.getPhotoUrl());
        }
        profile.setPhotoUrl(photoUrl);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toResponse(savedProfile);
    }

    private void validatePhoto(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("File is required.");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException("Maximum file size is 5MB.");
        }
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new BusinessException("Invalid file type.");
        }

    }
}
