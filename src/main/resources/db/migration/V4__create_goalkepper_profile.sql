CREATE TABLE goalkeeper_profiles (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     profile_id BIGINT NOT NULL UNIQUE,
                                     birth_date DATE,
                                     height_cm SMALLINT,
                                     preferred_foot VARCHAR(10),
                                     experience_level VARCHAR(20),
                                     bio VARCHAR(500),
                                     can_travel BOOLEAN NOT NULL DEFAULT FALSE,
                                     max_distance_km INT,
                                     expected_price DECIMAL(10,2),
                                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_goalkeeper_profile
                                         FOREIGN KEY (profile_id)
                                             REFERENCES profiles(id)
                                             ON DELETE CASCADE
);