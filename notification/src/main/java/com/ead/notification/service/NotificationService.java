package com.ead.notification.service;

import com.ead.notification.domain.Notification;
import com.ead.notification.domain.dto.request.NotificationUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    Page<Notification> findAllNotificationsByUserId(UUID userId, Pageable pageable);

    Notification updateStatusNotification(UUID notificationId, UUID userId, NotificationUpdateRequest notificationUpdateRequest);
}
