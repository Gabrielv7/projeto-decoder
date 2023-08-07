package com.ead.notification.mapper;

import com.ead.notification.model.Notification;
import com.ead.notification.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        return convertToResponse(notification);
    }

    public Page<NotificationResponse> convertToPageNotificationsResponse(Page<Notification> notifications) {
        return notifications.map(this::toResponse);
    }

    private NotificationResponse convertToResponse(Notification notification) {
        return new NotificationResponse(notification.getNotificationId(), notification.getUserId(), notification.getTitle(),
                notification.getMessage(), notification.getNotificationStatus(), convertDateOfPattern(notification.getCreationDate()));
    }

    private LocalDateTime convertDateOfPattern(LocalDateTime localDateTime) {
        return LocalDateTime.parse(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}

