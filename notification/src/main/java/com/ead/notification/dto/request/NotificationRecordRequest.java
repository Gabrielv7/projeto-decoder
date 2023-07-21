package com.ead.notification.dto.request;

import com.ead.notification.model.enums.NotificationStatusEnum;

import javax.validation.constraints.NotNull;

public record NotificationRecordRequest(@NotNull(message = "{notification.status.not.null}") NotificationStatusEnum notificationStatus) {

}
 