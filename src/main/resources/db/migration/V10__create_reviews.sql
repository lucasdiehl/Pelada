CREATE TABLE reviews (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         match_id BIGINT NOT NULL,
                         author_profile_id BIGINT NOT NULL,
                         target_profile_id BIGINT NOT NULL,
                         rating TINYINT NOT NULL,
                         comment VARCHAR(500),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_review_match
                             FOREIGN KEY (match_id)
                                 REFERENCES matches(id)
                                 ON DELETE CASCADE,
                         CONSTRAINT fk_review_author
                             FOREIGN KEY (author_profile_id)
                                 REFERENCES profiles(id)
                                 ON DELETE CASCADE,
                         CONSTRAINT fk_review_target
                             FOREIGN KEY (target_profile_id)
                                 REFERENCES profiles(id)
                                 ON DELETE CASCADE,
                         CONSTRAINT uk_review
                             UNIQUE (match_id, author_profile_id, target_profile_id)
);