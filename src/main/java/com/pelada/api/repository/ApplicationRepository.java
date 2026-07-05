package com.pelada.api.repository;

import com.pelada.api.entity.Application;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.Profile;
import com.pelada.api.enums.ApplicationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("""
    SELECT COUNT(a)
    FROM Application a
    WHERE a.goalkeeper.profile = :profile
    AND a.status = 'ACCEPTED'
    """)
    Long countAcceptedMatches(Profile profile);
    boolean existsByMatchAndStatus(Match match, ApplicationStatus status);
    long countByMatch(Match match);
    boolean existsByMatchAndGoalkeeper(Match match, GoalkeeperProfile goalkeeper);
    Slice<Application> findByMatch(Match match, Pageable pageable);
    List<Application> findAllByMatch(Match match);
    Slice<Application> findByGoalkeeper(GoalkeeperProfile goalkeeper, Pageable pageable);
}
