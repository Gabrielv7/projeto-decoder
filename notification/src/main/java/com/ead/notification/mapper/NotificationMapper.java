package com.ead.notification.mapper;

import com.ead.notification.model.Notification;
import com.ead.notification.dto.response.NotificationRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class NotificationMapper {

    public NotificationRecordResponse toResponse(Notification notification) {
        return convertToResponse(notification);
    }

    public Page<NotificationRecordResponse> convertToPageNotificationsResponse(Page<Notification> notifications) {
        return notifications.map(this::toResponse);
    }

    private NotificationRecordResponse convertToResponse(Notification notification) {
        return new NotificationRecordResponse(notification.getNotificationId(), notification.getUserId(), notification.getTitle(),
                notification.getMessage(), notification.getNotificationStatus(), convertDateOfPattern(notification.getCreationDate()));
    }

    private LocalDateTime convertDateOfPattern(LocalDateTime localDateTime) {
        return LocalDateTime.parse(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}

