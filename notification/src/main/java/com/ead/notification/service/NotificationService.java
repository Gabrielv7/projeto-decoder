package com.ead.notification.service;

import com.ead.notification.model.Notification;
import com.ead.notification.dto.rabbit.NotificationCommandRecordDto;
import com.ead.notification.dto.request.NotificationRecordRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    Notification saveNotification(NotificationCommandRecordDto notificationCommandRecordDto);

    Page<Notification> findAllNotificationsByUserId(UUID userId, Pageable pageable);

    Notification updateStatusNotification(UUID notificationId, UUID userId, NotificationRecordRequest notificationRecordRequest);
}
