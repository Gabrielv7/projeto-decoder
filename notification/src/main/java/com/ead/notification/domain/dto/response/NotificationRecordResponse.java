package com.ead.notification.domain.dto.response;

import com.ead.notification.domain.enums.NotificationStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NotificationRecordResponse(UUID notificationId,
                                         UUID userId,
                                         String title,
                                         String message,
                                         NotificationStatusEnum notificationStatus,
                                         LocalDateTime creationDate
                                         ) {

}

