package com.ead.notification.domain.dto.response;

import com.ead.notification.domain.enums.NotificationStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {

    private UUID notificationId;

    private UUID userId;

    private String title;

    private String message;

    private NotificationStatusEnum notificationStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

}
