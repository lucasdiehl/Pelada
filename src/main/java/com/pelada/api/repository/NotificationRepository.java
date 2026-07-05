package com.pelada.api.repository;

import com.pelada.api.entity.Notification;
import com.pelada.api.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Slice<Notification> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}