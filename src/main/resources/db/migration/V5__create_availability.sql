CREATE TABLE availability (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              goalkeeper_profile_id BIGINT NOT NULL,
                              day_of_week VARCHAR(15) NOT NULL,
                              UNIQUE (goalkeeper_profile_id, day_of_week),
                              CONSTRAINT fk_availability_goalkeeper
                                  FOREIGN KEY (goalkeeper_profile_id)
                                      REFERENCES goalkeeper_profiles(id)
                                      ON DELETE CASCADE
);