package com.ead.notification.service;

import com.ead.notification.model.Notification;
import com.ead.notification.dto.rabbit.NotificationCommandDto;
import com.ead.notification.dto.request.NotificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    void saveNotification(NotificationCommandDto notificationCommandDto);

    Page<Notification> findAllNotificationsByUserId(UUID userId, Pageable pageable);

    Notification updateStatusNotification(UUID notificationId, UUID userId, NotificationRequest notificationRequest);
}
