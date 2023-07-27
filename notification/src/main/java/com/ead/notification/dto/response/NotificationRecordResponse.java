package com.ead.notification.dto.response;

import com.ead.notification.model.enums.NotificationStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationRecordResponse(UUID notificationId,
                                         UUID userId,
                                         String title,
                                         String message,
                                         NotificationStatusEnum notificationStatus,
                                         LocalDateTime creationDate
                                         ) {

}

