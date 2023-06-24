package com.ead.notification.domain.dto.request;

import com.ead.notification.domain.enums.NotificationStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotificationUpdateRequest {

    @NotNull(message = "{notification.status.not.null}")
    private NotificationStatusEnum notificationStatus;

}
