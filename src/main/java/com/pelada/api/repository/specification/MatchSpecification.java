package com.pelada.api.repository.specification;

import com.pelada.api.dto.filter.MatchAdminFilter;
import com.pelada.api.dto.filter.MatchFilter;
import com.pelada.api.entity.Match;
import com.pelada.api.enums.MatchStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MatchSpecification {

    public static Specification<Match> withFilters(MatchFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    cb.equal(root.get("status"), MatchStatus.OPEN)
            );
            if (filter.city() != null) {
                predicates.add(
                        cb.equal(root.get("city"), filter.city())
                );
            }
            if (filter.date() != null) {
                predicates.add(
                        cb.equal(root.get("matchDate"), filter.date())
                );
            }
            if (filter.matchType() != null) {
                predicates.add(
                        cb.equal(root.get("matchType"), filter.matchType())
                );
            }
            if (filter.fieldType() != null) {
                predicates.add(
                        cb.equal(root.get("fieldType"), filter.fieldType())
                );
            }
            if (filter.skillLevel() != null) {
                predicates.add(
                        cb.equal(root.get("skillLevel"), filter.skillLevel())
                );
            }
            if (filter.minPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("price"),
                                filter.minPrice()
                        )
                );
            }
            if (filter.maxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("price"),
                                filter.maxPrice()
                        )
                );
            }
            if (StringUtils.hasText(filter.search())) {
                String search = "%" + filter.search().toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("title")), search),
                                cb.like(cb.lower(root.get("city")), search),
                                cb.like(cb.lower(root.get("fieldName")), search)
                        )
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Match> withAdminFilters(MatchAdminFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(filter.search())) {
                String search = "%" + filter.search().toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("title")), search),
                                cb.like(cb.lower(root.get("city")), search),
                                cb.like(cb.lower(root.get("fieldName")), search)
                        )
                );
            }
            if (StringUtils.hasText(filter.city())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("city")),
                                "%" + filter.city().toLowerCase() + "%"
                        )
                );
            }
            if (filter.date() != null) {
                predicates.add(
                        cb.equal(root.get("matchDate"), filter.date())
                );
            }
            if (filter.matchType() != null) {
                predicates.add(
                        cb.equal(root.get("matchType"), filter.matchType())
                );
            }
            if (filter.fieldType() != null) {
                predicates.add(
                        cb.equal(root.get("fieldType"), filter.fieldType())
                );
            }
            if (filter.skillLevel() != null) {
                predicates.add(
                        cb.equal(root.get("skillLevel"), filter.skillLevel())
                );
            }
            if (filter.status() != null) {
                predicates.add(
                        cb.equal(root.get("status"), filter.status())
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
