package com.ead.notification.mapper;

import com.ead.notification.dto.response.NotificationResponse;
import com.ead.notification.model.Notification;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);

}
