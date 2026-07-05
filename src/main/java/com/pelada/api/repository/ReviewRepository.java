package com.pelada.api.repository;

import com.pelada.api.entity.Profile;
import com.pelada.api.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    long countByTarget(Profile target);
    @Query("""
        SELECT AVG(r.rating)
        FROM Review r
        WHERE r.target = :target
    """)
    Double findAverageRating(Profile target);
    Page<Review> findByTarget(Profile target, Pageable pageable);
}
