CREATE TABLE goalkeeper_match_preferences (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              goalkeeper_profile_id BIGINT NOT NULL,
                                              match_type VARCHAR(20) NOT NULL,
                                              UNIQUE (goalkeeper_profile_id, match_type),
                                              CONSTRAINT fk_goalkeeper_match_preferences
                                                  FOREIGN KEY (goalkeeper_profile_id)
                                                      REFERENCES goalkeeper_profiles(id)
                                                      ON DELETE CASCADE
);