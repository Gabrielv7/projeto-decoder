package com.ead.notification.dto.request;

import javax.validation.constraints.NotBlank;

public record NotificationRequest(@NotBlank(message = "{notification.status.not.null}") String notificationStatus) {

}
 