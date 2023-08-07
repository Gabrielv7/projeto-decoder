package com.ead.notification.dto.request;

import javax.validation.constraints.NotBlank;

public record NotificationRecordRequest(@NotBlank(message = "{notification.status.not.null}") String notificationStatus) {

}
 