package com.pelada.api.controller;

import com.pelada.api.dto.request.CompleteProfileRequest;
import com.pelada.api.dto.request.UpdateProfileRequest;
import com.pelada.api.dto.response.ProfileResponse;
import com.pelada.api.entity.User;
import com.pelada.api.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profiles")
@Tag(name = "Profiles", description = "User profile management")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ProfileResponse completeProfile(@AuthenticationPrincipal User user, @RequestBody @Valid CompleteProfileRequest request) {
        return profileService.completeProfile(user, request);
    }

    @GetMapping("/me")
    public ProfileResponse getMyProfile(@AuthenticationPrincipal User user) {
        return profileService.getMyProfile(user);
    }

    @PutMapping("/me")
    public ProfileResponse updateMyProfile(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateProfileRequest request) {
        return profileService.updateMyProfile(user, request);
    }

    @PostMapping(value = "/me/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProfileResponse uploadPhoto(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
        return profileService.uploadPhoto(user, file);
    }

    @GetMapping("/users/{userId}")
    public ProfileResponse getProfileByUserId(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }

    @DeleteMapping("/delete")
    public void deleteMyProfile(@AuthenticationPrincipal User user) {
        profileService.deleteMyProfile(user);
    }
}
