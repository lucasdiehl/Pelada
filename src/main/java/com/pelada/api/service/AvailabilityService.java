package com.pelada.api.service;

import com.pelada.api.dto.request.UpdateAvailabilityRequest;
import com.pelada.api.dto.response.AvailabilityResponse;
import com.pelada.api.entity.User;
import jakarta.validation.Valid;

public interface AvailabilityService {
    AvailabilityResponse getAvailability(User user);
    AvailabilityResponse updateAvailability(User user, @Valid UpdateAvailabilityRequest request);
}
