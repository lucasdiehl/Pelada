package com.pelada.api.repository;

import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalkeeperRepository extends JpaRepository<GoalkeeperProfile, Long> {
    Optional<GoalkeeperProfile> findByProfile(Profile profile);
}
