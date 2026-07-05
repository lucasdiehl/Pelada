package com.pelada.api.entity;

import com.pelada.api.enums.ExperienceLevel;
import com.pelada.api.enums.PreferredFoot;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "goalkeeper_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalkeeperProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "height_cm")
    private Short heightCm;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_foot")
    private PreferredFoot preferredFoot;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    @Column(length = 500)
    private String bio;

    @Column(name = "can_travel", nullable = false)
    private Boolean canTravel;

    @Column(name = "max_distance_km")
    private Integer maxDistanceKm;

    @Column(name = "expected_price", precision = 10, scale = 2)
    private BigDecimal expectedPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        if (canTravel == null) {
            canTravel = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}