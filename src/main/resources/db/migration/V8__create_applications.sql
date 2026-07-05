CREATE TABLE applications (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              match_id BIGINT NOT NULL,
                              goalkeeper_profile_id BIGINT NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              CONSTRAINT fk_application_match
                                  FOREIGN KEY (match_id)
                                      REFERENCES matches(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT fk_application_goalkeeper
                                  FOREIGN KEY (goalkeeper_profile_id)
                                      REFERENCES goalkeeper_profiles(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT uk_match_goalkeeper
                                  UNIQUE (match_id, goalkeeper_profile_id)
);