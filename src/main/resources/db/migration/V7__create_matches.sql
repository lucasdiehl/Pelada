CREATE TABLE matches (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         organizer_profile_id BIGINT NOT NULL,
                         title VARCHAR(150) NOT NULL,
                         match_date DATE NOT NULL,
                         match_time TIME NOT NULL,
                         field_name VARCHAR(150) NOT NULL,
                         address VARCHAR(255),
                         city VARCHAR(100),
                         price DECIMAL(10,2),
                         match_type VARCHAR(30) NOT NULL,
                         field_type VARCHAR(30) NOT NULL,
                         skill_level VARCHAR(30) NOT NULL,
                         description VARCHAR(500),
                         status VARCHAR(30) NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         CONSTRAINT fk_matches_organizer
                             FOREIGN KEY (organizer_profile_id)
                                 REFERENCES profiles(id)
                                 ON DELETE CASCADE
);