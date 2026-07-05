package com.pelada.api.mapper;

import com.pelada.api.dto.response.AvailabilityResponse;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Set;

@Component
public class AvailabilityMapper {
    public AvailabilityResponse toResponse(Set<DayOfWeek> days) {
        return new AvailabilityResponse(days);
    }
}
