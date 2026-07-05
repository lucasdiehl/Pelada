package com.pelada.api.service.impl;

import com.pelada.api.dto.response.NotificationResponse;
import com.pelada.api.entity.*;
import com.pelada.api.enums.NotificationType;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.exception.ResourceNotFoundException;
import com.pelada.api.mapper.NotificationMapper;
import com.pelada.api.repository.NotificationRepository;
import com.pelada.api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    private void create(User user, NotificationType type, String message) {
        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .message(message)
                .isRead(false)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void notifyNewApplication(Match match, GoalkeeperProfile goalkeeper) {
        this.create(match.getOrganizer().getUser(), NotificationType.NEW_APPLICATION,
                String.format("%s se candidatou para a partida \"%s\".", goalkeeper.getProfile().getFullName(), match.getTitle()));
    }

    @Override
    public void notifyApplicationAccepted(Application application) {
        this.create(application.getGoalkeeper().getProfile().getUser(), NotificationType.APPLICATION_ACCEPTED,
                String.format("Sua candidatura para a partida \"%s\" foi aceita.", application.getMatch().getTitle()));
    }

    @Override
    public void notifyApplicationRejected(Application application) {
        this.create(application.getGoalkeeper().getProfile().getUser(), NotificationType.APPLICATION_REJECTED,
                String.format("Sua candidatura para a partida \"%s\" foi rejeitada.", application.getMatch().getTitle()));
    }

    @Override
    public void notifyMatchCancelled(Application application) {
        this.create(application.getGoalkeeper().getProfile().getUser(), NotificationType.MATCH_CANCELLED,
                String.format("A partida \"%s\" foi cancelada pelo organizador.", application.getMatch().getTitle()));
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<NotificationResponse> getMyNotifications(User user, Pageable pageable) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user, pageable).map(notificationMapper::toResponse);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId, User user) {
        Notification notification = getNotification(notificationId);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new BusinessException("You are not allowed to access this notification.");
        }
        if (Boolean.TRUE.equals(notification.getIsRead())) {
            return;
        }

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    private Notification getNotification(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found."));
    }
}
