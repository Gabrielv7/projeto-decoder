package com.ead.notification.mapper;

import com.ead.notification.domain.Notification;
import com.ead.notification.domain.dto.NotificationCommandDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationMapper {

    private final ModelMapper mapper;

    public Notification toEntity(NotificationCommandDto notificationCommandDto) {
        return mapper.map(notificationCommandDto, Notification.class);
    }

}

