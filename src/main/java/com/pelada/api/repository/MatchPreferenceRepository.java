package com.pelada.api.repository;

import com.pelada.api.entity.GoalkeeperMatchPreference;
import com.pelada.api.entity.GoalkeeperProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchPreferenceRepository extends JpaRepository<GoalkeeperMatchPreference, Long> {
    List<GoalkeeperMatchPreference> findAllByGoalkeeperProfile(GoalkeeperProfile goalkeeperProfile);
    void deleteAllByGoalkeeperProfile(GoalkeeperProfile goalkeeperProfile);
}
