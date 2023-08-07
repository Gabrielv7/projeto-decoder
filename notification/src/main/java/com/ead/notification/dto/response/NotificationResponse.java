package com.ead.notification.dto.response;

import com.ead.notification.model.enums.NotificationStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record NotificationResponse(UUID notificationId,
                                   UUID userId,
                                   String title,
                                   String message,
                                   NotificationStatusEnum notificationStatus,
                                   LocalDateTime creationDate
                                         ) {

}

