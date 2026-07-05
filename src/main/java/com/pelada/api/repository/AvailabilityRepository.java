package com.pelada.api.repository;

import com.pelada.api.entity.Availability;
import com.pelada.api.entity.GoalkeeperProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAllByGoalkeeperProfile(GoalkeeperProfile goalkeeperProfile);
    void deleteAllByGoalkeeperProfile(GoalkeeperProfile goalkeeperProfile);
}
