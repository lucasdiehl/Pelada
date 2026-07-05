package com.pelada.api.controller;

import com.pelada.api.dto.request.UpdateAvailabilityRequest;
import com.pelada.api.dto.response.AvailabilityResponse;
import com.pelada.api.entity.User;
import com.pelada.api.service.AvailabilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goalkeepers/me/availability")
@Tag(name = "Availability", description = "Goalkeeper weekly availability")
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @PreAuthorize("hasRole('GOALKEEPER')")
    @GetMapping
    public AvailabilityResponse getAvailability(@AuthenticationPrincipal User user) {
        return availabilityService.getAvailability(user);
    }

    @PreAuthorize("hasRole('GOALKEEPER')")
    @PutMapping
    public AvailabilityResponse updateAvailability(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateAvailabilityRequest request) {
        return availabilityService.updateAvailability(user, request);
    }
}