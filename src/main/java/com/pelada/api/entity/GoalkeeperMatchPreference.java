package com.pelada.api.entity;

import com.pelada.api.enums.MatchType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goalkeeper_match_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalkeeperMatchPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalkeeper_profile_id", nullable = false)
    private GoalkeeperProfile goalkeeperProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type", nullable = false)
    private MatchType matchType;

}