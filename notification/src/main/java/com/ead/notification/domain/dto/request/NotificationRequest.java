package com.ead.notification.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record NotificationRequest(@NotBlank(message = "{notification.status.not.null}") String notificationStatus) {

}
 