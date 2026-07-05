package com.pelada.api.service;

import com.pelada.api.dto.response.NotificationResponse;
import com.pelada.api.entity.Application;
import com.pelada.api.entity.GoalkeeperProfile;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationService {
    void notifyNewApplication(Match match, GoalkeeperProfile goalkeeper);
    void notifyApplicationAccepted(Application application);
    void notifyApplicationRejected(Application application);
    void notifyMatchCancelled(Application application);
    Slice<NotificationResponse> getMyNotifications(User user, Pageable pageable);
    void markAsRead(Long notificationId, User user);
}
