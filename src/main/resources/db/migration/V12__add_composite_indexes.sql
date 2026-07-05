-- applications: existsByMatchAndStatus → (match_id, status)
CREATE INDEX idx_applications_match_status
    ON applications (match_id, status);

-- applications: countAcceptedMatches filtra por status via goalkeeper_profile_id → (goalkeeper_profile_id, status)
CREATE INDEX idx_applications_goalkeeper_status
    ON applications (goalkeeper_profile_id, status);

-- matches: MatchSpecification sempre filtra status=OPEN, depois city e match_date → (status, city, match_date)
CREATE INDEX idx_matches_status_city_date
    ON matches (status, city, match_date);

-- matches: filtros por enums (status, match_type, field_type, skill_level) → cobre buscas por tipo/nível
CREATE INDEX idx_matches_status_type_field_skill
    ON matches (status, match_type, field_type, skill_level);

-- matches: findByOrganizer com paginação por data → (organizer_profile_id, match_date)
CREATE INDEX idx_matches_organizer_date
    ON matches (organizer_profile_id, match_date);

-- matches: filtro por price range quando status=OPEN → (status, price)
CREATE INDEX idx_matches_status_price
    ON matches (status, price);

-- notifications: findByUserOrderByCreatedAtDesc → (user_id, created_at DESC)
CREATE INDEX idx_notifications_user_created_at
    ON notifications (user_id, created_at DESC);

-- reviews: countByTarget + findAverageRating + findByTarget, todos por target_profile_id
-- inclui rating para cobrir AVG sem fetch extra de linha (covering index)
CREATE INDEX idx_reviews_target_rating
    ON reviews (target_profile_id, rating);

-- reviews: findByTarget com paginação ordena por created_at → (target_profile_id, created_at)
CREATE INDEX idx_reviews_target_created_at
    ON reviews (target_profile_id, created_at);

-- admin_logs: buscas por admin e período de auditoria → (admin_user_id, created_at)
CREATE INDEX idx_admin_logs_admin_created_at
    ON admin_logs (admin_user_id, created_at);

-- admin_logs: lookup de logs por entidade auditada → (target_type, target_id, created_at)
CREATE INDEX idx_admin_logs_target
    ON admin_logs (target_type, target_id, created_at);