package com.ead.notification.mapper;

import com.ead.notification.domain.Notification;
import com.ead.notification.domain.dto.rabbit.NotificationCommandDto;
import com.ead.notification.domain.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationMapper {

    private final ModelMapper mapper;

    public Notification convertMessage(NotificationCommandDto notificationCommandDto) {
        return mapper.map(notificationCommandDto, Notification.class);
    }

    public NotificationResponse toResponse(Notification notification) {
        return mapper.map(notification, NotificationResponse.class);
    }

    public Page<NotificationResponse> getMap(Page<Notification> notifications) {
        return notifications.map(this::toResponse);
    }

}

