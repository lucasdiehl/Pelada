package com.pelada.api.service.impl;

import com.pelada.api.dto.request.UpdateAvailabilityRequest;
import com.pelada.api.dto.response.AvailabilityResponse;
import com.pelada.api.entity.Availability;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.User;
import com.pelada.api.mapper.AvailabilityMapper;
import com.pelada.api.repository.AvailabilityRepository;
import com.pelada.api.service.AvailabilityService;
import com.pelada.api.service.GoalkeeperProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final GoalkeeperProfileService goalkeeperProfileService;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityResponse getAvailability(User user) {
        GoalkeeperProfile goalkeeperProfile = goalkeeperProfileService.getGoalkeeperProfile(user);
        Set<DayOfWeek> days = availabilityRepository
                .findAllByGoalkeeperProfile(goalkeeperProfile)
                .stream()
                .map(Availability::getDayOfWeek)
                .collect(Collectors.toSet());

        return availabilityMapper.toResponse(days);
    }

    public AvailabilityResponse updateAvailability(User user, UpdateAvailabilityRequest request) {
        GoalkeeperProfile goalkeeperProfile = goalkeeperProfileService.getGoalkeeperProfile(user);
        availabilityRepository.deleteAllByGoalkeeperProfile(goalkeeperProfile);
        List<Availability> availabilities = request.days()
                .stream()
                .map(day -> Availability.builder()
                        .goalkeeperProfile(goalkeeperProfile)
                        .dayOfWeek(day)
                        .build())
                .toList();
        availabilityRepository.saveAll(availabilities);

        return availabilityMapper.toResponse(request.days());
    }
}
