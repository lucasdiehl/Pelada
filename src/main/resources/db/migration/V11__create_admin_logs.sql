CREATE TABLE admin_logs (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            admin_user_id BIGINT NOT NULL,
                            action VARCHAR(50) NOT NULL,
                            target_type VARCHAR(30) NOT NULL,
                            target_id BIGINT NOT NULL,
                            description VARCHAR(500),
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_admin_logs_admin
                                FOREIGN KEY (admin_user_id)
                                    REFERENCES users(id)
                                    ON DELETE RESTRICT
);
