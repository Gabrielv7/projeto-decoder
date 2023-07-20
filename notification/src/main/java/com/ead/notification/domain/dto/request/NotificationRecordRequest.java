package com.ead.notification.domain.dto.request;

import com.ead.notification.domain.enums.NotificationStatusEnum;

import javax.validation.constraints.NotNull;

public record NotificationRecordRequest(@NotNull(message = "{notification.status.not.null}") NotificationStatusEnum notificationStatus) {

}
 