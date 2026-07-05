package com.pelada.api.repository;

import com.pelada.api.entity.Match;
import com.pelada.api.entity.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
    Slice<Match> findByOrganizer(Profile organizer, Pageable pageable);
}