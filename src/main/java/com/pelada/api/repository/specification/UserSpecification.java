package com.pelada.api.repository.specification;

import com.pelada.api.dto.filter.UserFilter;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> withFilters(UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(filter.search())) {
                String search = "%" + filter.search().toLowerCase() + "%";
                Join<User, Profile> profile = root.join("profile");
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("email")), search),
                                cb.like(cb.lower(profile.get("fullName")), search)
                        )
                );
            }
            if (filter.role() != null) {
                predicates.add(
                        cb.equal(root.get("role"), filter.role())
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
